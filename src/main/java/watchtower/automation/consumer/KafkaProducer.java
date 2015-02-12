package watchtower.automation.consumer;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.automation.configuration.KafkaProducerConfiguration;
import watchtower.common.automation.Job;
import watchtower.common.automation.JobResult;

public class KafkaProducer {
  private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
  
  private final KafkaProducerConfiguration producerConfiguration;
  private final Producer<String, String> producer;
  
  public KafkaProducer(KafkaProducerConfiguration producerConfiguration) {
    this.producerConfiguration = producerConfiguration;
    ProducerConfig producerConfig = new ProducerConfig(getKafkaProperties());
    producer = new Producer<String, String>(producerConfig);
  }
  
  public void send(Job job, JobResult result) {
    logger.debug("Sending job results for {}: {}", job.getId(), result.toString());
    
    final KeyedMessage<String, String> message =
        new KeyedMessage<String, String>(producerConfiguration.getTopic(), job.getId(), result.toString());
    
    producer.send(message);
  }
  
  private Properties getKafkaProperties() {
    Properties properties = new Properties();

    properties.put("metadata.broker.list", producerConfiguration.getMetadataBrokerList());
    properties.put("request.required.acks", producerConfiguration.getRequestRequiredAcks().toString());
    properties.put("request.timeout.ms", producerConfiguration.getRequestTimeoutMs().toString());
    properties.put("producer.type", producerConfiguration.getProducerType());
    properties.put("serializer.class", producerConfiguration.getSerializerClass());
    if (producerConfiguration.getKeySerializerClass() != null && !producerConfiguration.getKeySerializerClass().isEmpty())
      properties.put("key.serializer.class", producerConfiguration.getKeySerializerClass());
    if (producerConfiguration.getPartitionerClass() != null && !producerConfiguration.getPartitionerClass().isEmpty())
      properties.put("partitioner.class", producerConfiguration.getPartitionerClass());
    properties.put("compression.codec", producerConfiguration.getCompressionCodec());
    properties.put("compressed.topics", producerConfiguration.getCompressedTopics());
    properties.put("message.send.max.retries", producerConfiguration.getMessageSendMaxRetries().toString());
    properties.put("retry.backoff.ms", producerConfiguration.getRetryBackoffMs().toString());
    properties.put("topic.metadata.refresh.interval.ms", producerConfiguration.getTopicMetadataRefreshIntervalMs().toString());
    properties.put("queue.buffering.max.ms", producerConfiguration.getQueueBufferingMaxMs().toString());
    properties.put("queue.buffering.max.messages", producerConfiguration.getQueueBufferingMaxMessages().toString());
    properties.put("queue.enqueue.timeout.ms", producerConfiguration.getQueueEnqueueTimeoutMs().toString());
    properties.put("batch.num.messages", producerConfiguration.getBatchNumMessages().toString());
    properties.put("send.buffer.bytes", producerConfiguration.getSendBufferBytes().toString());
    properties.put("client.id", producerConfiguration.getClientId());

    return properties;
  }
}