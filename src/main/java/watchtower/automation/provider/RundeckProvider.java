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
package watchtower.automation.provider;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import watchtower.automation.configuration.WatchtowerAutomationConfiguration;
import watchtower.automation.producer.KafkaProducer;
import watchtower.common.automation.Job;

public class RundeckProvider extends Provider {
  @Inject
  private RundeckProviderRunnableFactory rundeckProviderRunnableFactory;
  
  @Inject
  public RundeckProvider(@Assisted WatchtowerAutomationConfiguration configuration, @Assisted KafkaProducer kafkaProducer) {
    super(configuration, kafkaProducer);
  }

  @Override
  protected ProviderRunnable createRunnable(Job job) {
    return rundeckProviderRunnableFactory.create(providerConfiguration, kafkaProducer, job);
  }
}