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

import com.google.inject.Guice;
import com.google.inject.Injector;

import watchtower.automation.configuration.WatchtowerAutomationConfiguration;
import watchtower.automation.consumer.KafkaCommandsConsumer;
import watchtower.automation.health.KafkaHealthCheck;
import watchtower.automation.provider.Provider;
import watchtower.automation.resources.ExecutionsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WatchtowerAutomationApplication extends Application<WatchtowerAutomationConfiguration> {
  
  public static void main(String[] args) throws Exception {
    new WatchtowerAutomationApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<WatchtowerAutomationConfiguration> bootstrap) {
  }

  @Override
  public void run(WatchtowerAutomationConfiguration configuration, Environment environment) throws Exception {
    Injector injector = Guice.createInjector(new WatchtowerAutomationModule(configuration, environment));
    
    environment.jersey().register(injector.getInstance(ExecutionsResource.class));
    
    environment.healthChecks().register("kafka-health-check", KafkaHealthCheck.getInstance());
    
    final Provider provider = injector.getInstance(Provider.class);

    environment.lifecycle().manage(provider);
    
    final KafkaCommandsConsumer kafkaEventsConsumer = injector.getInstance(KafkaCommandsConsumer.class);
    
    environment.lifecycle().manage(kafkaEventsConsumer);
  }

  @Override
  public String getName() {
    return "watchtower-automation";
  }
}