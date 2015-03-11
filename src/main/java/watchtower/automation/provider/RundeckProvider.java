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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import watchtower.automation.configuration.WatchtowerAutomationConfiguration;
import watchtower.automation.producer.KafkaProducer;
import watchtower.common.automation.Job;

@Singleton
public class RundeckProvider extends Provider {
  private static final Logger logger = LoggerFactory.getLogger(Provider.class);
  
  @Inject
  private RundeckProviderRunnableFactory rundeckProviderRunnableFactory;
  
  @Inject
  public RundeckProvider(WatchtowerAutomationConfiguration configuration, KafkaProducer kafkaProducer) {
    super(configuration, kafkaProducer);
  }

  @Override
  protected ProviderRunnable createRunnable(Job job) {
    logger.info("Creating runnable in provider # " + this.hashCode());
    return rundeckProviderRunnableFactory.create(providerConfiguration, kafkaProducer, job);
  }
}