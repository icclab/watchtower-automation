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
package watchtower.automation.provider.rundeck;

import java.util.concurrent.TimeUnit;

import org.rundeck.api.OptionsBuilder;
import org.rundeck.api.RunJobBuilder;
import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.automation.configuration.ProviderConfiguration;
import watchtower.automation.configuration.RundeckProviderConfiguration;
import watchtower.automation.producer.KafkaProducer;
import watchtower.automation.provider.ProviderRunnable;
import watchtower.common.automation.Job;
import watchtower.common.automation.JobExecution;
import watchtower.common.automation.JobExecutionStatus;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class RundeckProviderRunnable extends ProviderRunnable {
  private static final Logger logger = LoggerFactory.getLogger(RundeckProviderRunnable.class);
  private RundeckClient rundeckClient;
  private RundeckExecution rundeckExecution;

  @Inject
  public RundeckProviderRunnable(@Assisted ProviderConfiguration providerConfiguration,
      @Assisted KafkaProducer kafkaProducer, @Assisted Job job) {
    super(providerConfiguration, kafkaProducer, job);

    RundeckProviderConfiguration rundeckProviderConfiguration =
        (RundeckProviderConfiguration) providerConfiguration;

    logger.info("Logging in with {}", rundeckProviderConfiguration);

    if (rundeckProviderConfiguration.getToken() == null
        || rundeckProviderConfiguration.getToken().isEmpty()) {
      rundeckClient =
          RundeckClient
              .builder()
              .url(rundeckProviderConfiguration.getUrl())
              .login(rundeckProviderConfiguration.getUsername(),
                  rundeckProviderConfiguration.getPassword()).build();
    } else
      rundeckClient =
          RundeckClient.builder().url(rundeckProviderConfiguration.getUrl())
              .token(rundeckProviderConfiguration.getToken()).build();
  }

  public void run() {
    logger.info("Starting automation job: {}", job);

    try {
      OptionsBuilder optionBuilder = new OptionsBuilder();

      if (job.getParameters() != null)
        for (String key : job.getParameters().keySet())
          optionBuilder.addOption(key, job.getParameters().get(key));

      rundeckExecution =
          rundeckClient.runJob(
              RunJobBuilder.builder().setJobId(job.getJobId())
                  .setOptions(optionBuilder.toProperties()).build(), 1, TimeUnit.SECONDS);

      JobExecution execution =
          new JobExecution(rundeckExecution.getId().toString(), job.getId(),
              rundeckExecution.toString(), JobExecutionStatus.valueOf(rundeckExecution.getStatus()
                  .toString()));

      returnJobExecution(execution);

      logger.info("Execution finished: {}", rundeckExecution.toString());
    } catch (Exception e) {
      logger.info("Failed to run automation job: {}", e);
    }
  }
}