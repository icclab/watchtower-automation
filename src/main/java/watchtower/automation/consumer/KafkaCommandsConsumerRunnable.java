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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import kafka.consumer.KafkaStream;
import watchtower.automation.provider.Provider;
import watchtower.common.automation.Command;
import watchtower.common.automation.ExecuteJobCommand;

public class KafkaCommandsConsumerRunnable extends KafkaConsumerRunnable<Command> {
  private static final Logger logger = LoggerFactory.getLogger(KafkaCommandsConsumerRunnable.class);
  private final ObjectMapper objectMapper;
  
  @Inject
  public KafkaCommandsConsumerRunnable(@Assisted KafkaStream<byte[], byte[]> stream, @Assisted int threadNumber, Provider provider) {
    super(stream, threadNumber, provider);
    
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
  }

  @Override
  protected void consumeMessage(String message) {
    try {
      final Command command = objectMapper.readValue(message, Command.class);
      
      if (command instanceof ExecuteJobCommand)
        provider.executeJob(((ExecuteJobCommand) command).getJob());
      
      logger.debug("{}", command);
    } catch (Exception e) {
      logger.error("Failed to deserialize JSON message: " + message, e);
    }
  }
}