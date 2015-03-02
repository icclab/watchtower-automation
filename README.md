*Please note that all watchtower components are under heavy development and the norm is that things will break. Please be patient with us until the first stable release.*

# Overview

**watchtower-automation** is a component of **watchtower** which handles communication with automation engines. It consumes commands from the Kafka commands queue and it runs them against the automation engines.

Currently, it only supports Rundeck for job execution.

### Build Instructions

The best way to install **watchtower-automation** is to download and build it with Maven. Please not it depends on **watchtower-common** which needs to be installed prior to it.

```
git clone https://github.com/icclab/watchtower-automation.git
cd watchtower-automation
mvn clean install
```

Besides the `jar` file, an additional output is the `deb` package which can be easility installed on Debian based linux. It will add a **watchtower-automation** service, however it first needs to be configured by editing and renaming ```watchtower-automation-config.yml-sample``` in ```/etc/watchtower```.

# License

Copyright 2015 Zurich University of Applied Sciences

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied.
See the License for the specific language governing permissions and
limitations under the License.