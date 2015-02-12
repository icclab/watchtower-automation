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

import javax.validation.constraints.NotNull;

public class RundeckProviderConfiguration extends ProviderConfiguration implements Serializable {
  
  private static final long serialVersionUID = -5516729044896223698L;

  @NotNull
  private String url;
  
  private String username;
  private String password;
  private String token;
  
  @NotNull
  private String project;
  
  @Override
  public String getProviderName() {
    return "Rundeck";
  }
  
  public String getUrl() {
    return url;
  }
  
  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public String getToken() {
    return token;
  }
  
  public String getProject() {
    return project;
  }
}