/*
 * Copyright 2015 Zurich University of Applied Sciences
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package watchtower.automation.consumer;

import io.dropwizard.lifecycle.Managed;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import watchtower.automation.configuration.KafkaConsumerConfiguration;
import watchtower.automation.configuration.WatchtowerAutomationConfiguration;
import watchtower.automation.provider.Provider;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public abstract class KafkaConsumer<T> implements Managed {
  private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
  
  protected final KafkaConsumerConfiguration kafkaConfiguration;
  private final ConsumerConnector consumerConnector;
  private ExecutorService executorService;
  protected final Provider provider;
  
  @Inject
  public KafkaConsumer(@Assisted WatchtowerAutomationConfiguration configuration, @Assisted Provider provider) {
    kafkaConfiguration = configuration.getKafkaConsumerConfiguration();
    consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(getKafkaProperties()));
    this.provider = provider;
  }
  
  protected abstract KafkaConsumerRunnable<T> createRunnable(KafkaStream<byte[], byte[]> stream,
      int threadNumber);
  
  public void start() throws Exception {
    executorService = Executors.newFixedThreadPool(kafkaConfiguration.getNumThreads());
    
    int threadNumber = 0;
    for (final KafkaStream<byte[], byte[]> stream : getKafkaStreams())
      executorService.submit(createRunnable(stream, threadNumber++));
  }

  public void stop() throws Exception {
    if (executorService != null) {
      executorService.shutdown();
      
      try {
        if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
          executorService.shutdownNow();
          
          if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
            logger.debug("ExecutorService did not terminate");
        }
      } catch (InterruptedException ie) {
        executorService.shutdownNow();
        
        logger.debug("ExecutorService did not terminate", ie);
      }
    }
  }
  
  private List<KafkaStream<byte[], byte[]>> getKafkaStreams() {
    Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams =
        this.consumerConnector.createMessageStreams(ImmutableMap.of(getTopic(), kafkaConfiguration.getNumThreads()));
    
    List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(getTopic());
    
    if (streams.size() == 0)
      throw new IllegalStateException(String.format("No stream found"));

    return streams;
  }
  
  protected abstract String getTopic();
  
  private Properties getKafkaProperties() {
    Properties properties = new Properties();

    properties.put("group.id", kafkaConfiguration.getGroupId());
    properties.put("zookeeper.connect", kafkaConfiguration.getZookeeperConnect());
    properties.put("consumer.id", kafkaConfiguration.getConsumerId());
    properties.put("socket.timeout.ms", kafkaConfiguration.getSocketTimeoutMs().toString());
    properties.put("socket.receive.buffer.bytes", kafkaConfiguration.getSocketReceiveBufferBytes().toString());
    properties.put("fetch.message.max.bytes", kafkaConfiguration.getFetchMessageMaxBytes().toString());
    properties.put("auto.commit.enable", kafkaConfiguration.getAutoCommitEnable().toString());
    properties.put("auto.commit.interval.ms", kafkaConfiguration.getAutoCommitIntervalMs().toString());
    properties.put("queued.max.message.chunks", kafkaConfiguration.getQueuedMaxMessageChunks().toString());
    properties.put("rebalance.max.retries", kafkaConfiguration.getRebalanceMaxRetries().toString());
    properties.put("fetch.min.bytes", kafkaConfiguration.getFetchMinBytes().toString());
    properties.put("fetch.wait.max.ms", kafkaConfiguration.getFetchWaitMaxMs().toString());
    properties.put("rebalance.backoff.ms", kafkaConfiguration.getRebalanceBackoffMs().toString());
    properties.put("refresh.leader.backoff.ms", kafkaConfiguration.getRefreshLeaderBackoffMs().toString());
    properties.put("auto.offset.reset", kafkaConfiguration.getAutoOffsetReset());
    properties.put("consumer.timeout.ms", kafkaConfiguration.getConsumerTimeoutMs().toString());
    properties.put("client.id", kafkaConfiguration.getClientId());
    properties.put("zookeeper.session.timeout.ms", kafkaConfiguration.getZookeeperSessionTimeoutMs().toString());
    properties.put("zookeeper.connection.timeout.ms", kafkaConfiguration.getZookeeperConnectionTimeoutMs().toString());
    properties.put("zookeeper.sync.time.ms", kafkaConfiguration.getZookeeperSyncTimeMs().toString());

    return properties;
  }
}