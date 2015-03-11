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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import watchtower.automation.configuration.ProviderConfiguration;
import watchtower.automation.configuration.WatchtowerAutomationConfiguration;
import watchtower.automation.producer.KafkaProducer;
import watchtower.common.automation.Job;
import io.dropwizard.lifecycle.Managed;

public abstract class Provider implements Managed {
  private static final Logger logger = LoggerFactory.getLogger(Provider.class);
  
  protected final ProviderConfiguration providerConfiguration;
  protected final KafkaProducer kafkaProducer;
  private ExecutorService executorService;
  
  @Inject
  public Provider(@Assisted WatchtowerAutomationConfiguration configuration, @Assisted KafkaProducer kafkaProducer) {
    this.providerConfiguration = configuration.getAutomationProviderConfiguration();
    this.kafkaProducer = kafkaProducer;
  }

  public void start() throws Exception {
    executorService = Executors.newFixedThreadPool(providerConfiguration.getNumThreads());
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
  
  public void runJob(Job job) {    
    try {
      executorService.submit(createRunnable(job));
    } catch (RejectedExecutionException ree) {
      logger.debug("Failed to submit runnable: {}", ree);
    }
  }
  
  protected abstract ProviderRunnable createRunnable(Job job);
}