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

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.automation.health.KafkaHealthCheck;
import watchtower.automation.provider.Provider;

public abstract class KafkaConsumerRunnable<T> implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerRunnable.class);
  
  private final KafkaStream<byte[], byte[]> stream;
  private final int threadNumber;
  protected final Provider provider;
  private volatile boolean running;
  
  public KafkaConsumerRunnable(KafkaStream<byte[], byte[]> stream, int threadNumber, Provider provider) {
    this.stream = stream;
    this.threadNumber = threadNumber;
    this.provider = provider;
  }

  public void run() {
    logger.debug("KafkaChannel {} has stream", this.threadNumber);
    final ConsumerIterator<byte[], byte[]> streamIterator = stream.iterator();
    
    running = true;
    
    while (running) {
      try {
        if (streamIterator.hasNext()) {
          final byte[] message = streamIterator.next().message();
          
          logger.debug("Thread {}: {}", threadNumber, message.toString());
          
          consumeMessage(message);
        }
      } catch (ConsumerTimeoutException cte) {
        logger.debug("Timed out when consuming from Kafka", cte);
        
        KafkaHealthCheck.getInstance().heartAttack(cte.getMessage());
      }
    }
  }
  
  abstract protected void consumeMessage(byte[] message);
}