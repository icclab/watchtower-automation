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

public class KafkaProducerConfiguration implements Serializable {

  private static final long serialVersionUID = -576317721403416680L;

  @NotEmpty
  String topic;

  @NotEmpty
  String metadataBrokerList;

  @NotEmpty
  Integer requestRequiredAcks;

  @NotEmpty
  Integer requestTimeoutMs;

  @NotEmpty
  String producerType;

  @NotEmpty
  String serializerClass;

  @NotEmpty
  String keySerializerClass;

  @NotEmpty
  String partitionerClass;

  @NotEmpty
  String compressionCodec;

  @NotEmpty
  String compressedTopics;

  @NotEmpty
  Integer messageSendMaxRetries;

  @NotEmpty
  Integer retryBackoffMs;

  @NotEmpty
  Integer topicMetadataRefreshIntervalMs;

  @NotEmpty
  Integer queueBufferingMaxMs;

  @NotEmpty
  Integer queueBufferingMaxMessages;

  @NotEmpty
  Integer queueEnqueueTimeoutMs;

  @NotEmpty
  Integer batchNumMessages;

  @NotEmpty
  Integer sendBufferBytes;

  @NotEmpty
  String clientId;


  public String getTopic() {
      return topic;
  }

  public String getMetadataBrokerList() {
      return metadataBrokerList;
  }

  public Integer getRequestRequiredAcks() {
      return requestRequiredAcks;
  }

  public Integer getRequestTimeoutMs() {
      return requestTimeoutMs;
  }

  public String getProducerType() {
      return producerType;
  }

  public String getSerializerClass() {
      return serializerClass;
  }

  public String getKeySerializerClass() {
      return keySerializerClass;
  }

  public String getPartitionerClass() {
      return partitionerClass;
  }

  public String getCompressionCodec() {
      return compressionCodec;
  }

  public String getCompressedTopics() {
      return compressedTopics;
  }

  public Integer getMessageSendMaxRetries() {
      return messageSendMaxRetries;
  }

  public Integer getRetryBackoffMs() {
      return retryBackoffMs;
  }

  public Integer getTopicMetadataRefreshIntervalMs() {
      return topicMetadataRefreshIntervalMs;
  }

  public Integer getQueueBufferingMaxMs() {
      return queueBufferingMaxMs;
  }

  public Integer getQueueBufferingMaxMessages() {
      return queueBufferingMaxMessages;
  }

  public Integer getQueueEnqueueTimeoutMs() {
      return queueEnqueueTimeoutMs;
  }

  public Integer getBatchNumMessages() {
      return batchNumMessages;
  }

  public Integer getSendBufferBytes() {
      return sendBufferBytes;
  }

  public String getClientId() {
      return clientId;
  }
}