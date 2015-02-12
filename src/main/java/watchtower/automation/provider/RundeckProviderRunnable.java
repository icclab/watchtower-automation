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

import java.util.concurrent.TimeUnit;

import org.rundeck.api.OptionsBuilder;
import org.rundeck.api.RunJobBuilder;
import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.automation.configuration.ProviderConfiguration;
import watchtower.automation.configuration.RundeckProviderConfiguration;
import watchtower.common.automation.Job;

public class RundeckProviderRunnable extends ProviderRunnable {
  private static final Logger logger = LoggerFactory.getLogger(RundeckProviderRunnable.class);
  private RundeckClient rundeckClient;
  private RundeckExecution rundeckExecution;
  
  @SuppressWarnings("deprecation")
  public RundeckProviderRunnable(ProviderConfiguration providerConfiguration, Job job) {
    super(providerConfiguration, job);
    
    RundeckProviderConfiguration rundeckProviderConfiguration = (RundeckProviderConfiguration) providerConfiguration;
    
    if (rundeckProviderConfiguration.getToken().isEmpty())
      rundeckClient = new RundeckClient(rundeckProviderConfiguration.getUrl(), 
          rundeckProviderConfiguration.getUsername(), rundeckProviderConfiguration.getPassword());
    else
      rundeckClient = new RundeckClient(rundeckProviderConfiguration.getUrl(),
          rundeckProviderConfiguration.getToken());
  }

  public void run() {
    logger.debug("Starting automation job: {}", job);
    
    OptionsBuilder optionBuilder = new OptionsBuilder();
    
    for (String key : job.getParameters().keySet())
      optionBuilder.addOption(key, job.getParameters().get(key));
    
    try {
      rundeckExecution = rundeckClient.runJob(RunJobBuilder.builder()
          .setJobId(job.getId()).setOptions(optionBuilder.toProperties())
          .build(), 1, TimeUnit.MINUTES);
      logger.debug("Execution finished: {}", rundeckExecution.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}