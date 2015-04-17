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

import watchtower.automation.configuration.ProviderConfiguration;
import watchtower.automation.producer.KafkaProducer;
import watchtower.common.automation.Job;
import watchtower.common.automation.JobExecution;

public abstract class ProviderRunnable implements Runnable {
  protected final ProviderConfiguration providerConfiguration;
  private final KafkaProducer kafkaProducer;
  protected final Job job;

  public ProviderRunnable(ProviderConfiguration providerConfiguration, KafkaProducer kafkaProducer,
      Job job) {
    this.providerConfiguration = providerConfiguration;
    this.kafkaProducer = kafkaProducer;
    this.job = job;
  }

  public void returnJobExecution(JobExecution execution) {
    kafkaProducer.send(job.getIncidentId(), execution);
  }
}