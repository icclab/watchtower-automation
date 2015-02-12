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

import kafka.consumer.KafkaStream;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import watchtower.automation.configuration.WatchtowerAutomationConfiguration;
import watchtower.automation.provider.Provider;
import watchtower.common.automation.Command;

public class KafkaCommandsConsumer extends KafkaConsumer<Command> {
  @Inject
  private KafkaCommandsConsumerRunnableFactory commandConsumerRunnableFactory;
  @Inject
  private Provider provider;
  
  @Inject
  public KafkaCommandsConsumer(@Assisted WatchtowerAutomationConfiguration configuration, @Assisted Provider provider) {
    super(configuration, provider);
  }

  @Override
  protected KafkaConsumerRunnable<Command> createRunnable(KafkaStream<byte[], byte[]> stream,
      int threadNumber) {
    return commandConsumerRunnableFactory.create(stream, threadNumber, provider);
  }

  @Override
  protected String getTopic() {
    return kafkaConfiguration.getTopic();
  }
}