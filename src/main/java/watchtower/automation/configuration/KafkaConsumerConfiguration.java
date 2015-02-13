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
package watchtower.automation.configuration;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class KafkaConsumerConfiguration implements Serializable {

  private static final long serialVersionUID = 6312614514245347452L;

  @NotEmpty
  String topic;

  @NotEmpty
  Integer numThreads;

  @NotEmpty
  String groupId;

  @NotEmpty
  String zookeeperConnect;

  @NotEmpty
  String consumerId;

  @NotEmpty
  Integer socketTimeoutMs;

  @NotEmpty
  Integer socketReceiveBufferBytes;

  @NotEmpty
  Integer fetchMessageMaxBytes;

  @NotEmpty
  Boolean autoCommitEnable;

  @NotEmpty
  Integer autoCommitIntervalMs;

  @NotEmpty
  Integer queuedMaxMessageChunks;

  @NotEmpty
  Integer rebalanceMaxRetries;

  @NotEmpty
  Integer fetchMinBytes;

  @NotEmpty
  Integer fetchWaitMaxMs;

  @NotEmpty
  Integer rebalanceBackoffMs;

  @NotEmpty
  Integer refreshLeaderBackoffMs;

  @NotEmpty
  String autoOffsetReset;

  @NotEmpty
  Integer consumerTimeoutMs;

  @NotEmpty
  String clientId;

  @NotEmpty
  Integer zookeeperSessionTimeoutMs;

  @NotEmpty
  Integer zookeeperConnectionTimeoutMs;

  @NotEmpty
  Integer zookeeperSyncTimeMs;

  public String getTopic() {
      return topic;
  }

  public Integer getNumThreads() {
      return numThreads;
  }

  public String getGroupId() {
      return groupId;
  }

  public String getZookeeperConnect() {
      return zookeeperConnect;
  }

  public String getConsumerId() {
      return consumerId;
  }

  public Integer getSocketTimeoutMs() {
      return socketTimeoutMs;
  }

  public Integer getSocketReceiveBufferBytes() {
      return socketReceiveBufferBytes;
  }

  public Integer getFetchMessageMaxBytes() {
      return fetchMessageMaxBytes;
  }

  public Boolean getAutoCommitEnable() {
      return autoCommitEnable;
  }

  public Integer getAutoCommitIntervalMs() {
      return autoCommitIntervalMs;
  }

  public Integer getQueuedMaxMessageChunks() {
      return queuedMaxMessageChunks;
  }

  public Integer getRebalanceMaxRetries() {
      return rebalanceMaxRetries;
  }

  public Integer getFetchMinBytes() {
      return fetchMinBytes;
  }

  public Integer getFetchWaitMaxMs() {
      return fetchWaitMaxMs;
  }

  public Integer getRebalanceBackoffMs() {
      return rebalanceBackoffMs;
  }

  public Integer getRefreshLeaderBackoffMs() {
      return refreshLeaderBackoffMs;
  }

  public String getAutoOffsetReset() {
      return autoOffsetReset;
  }

  public Integer getConsumerTimeoutMs() {
      return consumerTimeoutMs;
  }

  public String getClientId() {
      return clientId;
  }

  public Integer getZookeeperSessionTimeoutMs() {
      return zookeeperSessionTimeoutMs;
  }

  public Integer getZookeeperConnectionTimeoutMs() {
      return zookeeperConnectionTimeoutMs;
  }

  public Integer getZookeeperSyncTimeMs() {
      return zookeeperSyncTimeMs;
  }
}