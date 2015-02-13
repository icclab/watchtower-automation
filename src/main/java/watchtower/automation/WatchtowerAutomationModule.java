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
package watchtower.automation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.automation.configuration.WatchtowerAutomationConfiguration;
import watchtower.automation.consumer.KafkaCommandsConsumer;
import watchtower.automation.consumer.KafkaCommandsConsumerFactory;
import watchtower.automation.consumer.KafkaCommandsConsumerRunnable;
import watchtower.automation.consumer.KafkaCommandsConsumerRunnableFactory;
import watchtower.automation.producer.KafkaProducer;
import watchtower.automation.producer.KafkaProducerFactory;
import watchtower.automation.provider.Provider;
import watchtower.automation.provider.ProviderRunnable;
import watchtower.automation.provider.RundeckProvider;
import watchtower.automation.provider.RundeckProviderFactory;
import watchtower.automation.provider.RundeckProviderRunnable;
import watchtower.automation.provider.RundeckProviderRunnableFactory;
import io.dropwizard.setup.Environment;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class WatchtowerAutomationModule extends AbstractModule {
  private static final Logger logger = LoggerFactory.getLogger(WatchtowerAutomationModule.class);
      
  private final WatchtowerAutomationConfiguration configuration;
  private final Environment environment;
  
  public WatchtowerAutomationModule(WatchtowerAutomationConfiguration configuration, Environment environment) {
    this.configuration = configuration;
    this.environment = environment;
  }

  @Override
  protected void configure() {
    bind(WatchtowerAutomationConfiguration.class).toInstance(configuration);
    bind(Environment.class).toInstance(environment);
    
    install(new FactoryModuleBuilder().implement(KafkaCommandsConsumerRunnable.class, KafkaCommandsConsumerRunnable.class)
        .build(KafkaCommandsConsumerRunnableFactory.class));
    
    install(new FactoryModuleBuilder().implement(KafkaCommandsConsumer.class, KafkaCommandsConsumer.class)
        .build(KafkaCommandsConsumerFactory.class));
    
    install(new FactoryModuleBuilder().implement(KafkaProducer.class, KafkaProducer.class)
        .build(KafkaProducerFactory.class));
    
    if (configuration.getAutomationProvider().equalsIgnoreCase("rundeck")) {
      install(new FactoryModuleBuilder().implement(RundeckProviderRunnable.class, RundeckProviderRunnable.class)
          .build(RundeckProviderRunnableFactory.class));
      
      install(new FactoryModuleBuilder().implement(RundeckProvider.class, RundeckProvider.class)
          .build(RundeckProviderFactory.class));
      
      bind(Provider.class).to(RundeckProvider.class);
      bind(ProviderRunnable.class).to(RundeckProviderRunnable.class);
    } else {
      logger.debug("Encountered unknown automation provider: {}", configuration.getAutomationProvider());
      
      System.exit(1);
    }
  }
}