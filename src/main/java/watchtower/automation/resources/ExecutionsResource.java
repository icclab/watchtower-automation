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
package watchtower.automation.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.automation.provider.Provider;
import watchtower.common.automation.Job;
import watchtower.common.automation.JobExecution;
import watchtower.common.automation.JobExecutionStatus;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

@Path("/v1.0/executions")
public class ExecutionsResource {
  private static final Logger logger = LoggerFactory.getLogger(ExecutionsResource.class);

  private Provider provider;

  @Inject
  public ExecutionsResource(Provider provider) {
    this.provider = provider;
  }

  @POST
  @Timed
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response submitExecution(@Context UriInfo uriInfo, Job job) {
    logger.info("Received {}", job);

    provider.runJob(job);

    return Response.ok().build();
  }

  @GET
  @Timed
  @PathParam("/{executionId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getExecution(@Context UriInfo uriInfo, @PathParam("executionId") String id,
      @QueryParam("executionStatus") String executionStatus, String executionOutput) {

    JobExecution execution =
        new JobExecution(id, null, executionOutput, JobExecutionStatus.valueOf(executionStatus));

    logger.info("Received {}", execution);

    // kafkaProducer.send(execution);

    return Response.ok().build();
  }
}