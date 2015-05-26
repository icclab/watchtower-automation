*Please note that all watchtower components are under heavy development and the norm is that things will break. Please be patient with us until the first stable release.*

<div align="center">
	<img src="https://raw.githubusercontent.com/icclab/watchtower-common/master/watchtower.png" alt="Watchtower" title="Watchtower">
</div>

[![Build Status](https://travis-ci.org/icclab/watchtower-automation.svg?branch=master)](https://travis-ci.org/icclab/watchtower-automation)
[![Coverage Status](https://coveralls.io/repos/icclab/watchtower-automation/badge.svg?branch=master)](https://coveralls.io/r/icclab/watchtower-automation?branch=master)

# Overview

**watchtower-automation** is a component of **watchtower** which handles communication with automation engines. It consumes commands from the Kafka commands queue and it runs them against the automation engines.

Currently, it only supports Rundeck for job execution.

## General Instructions

### Building

The best way to install **watchtower-automation** is to download and build it with Maven. Please note it depends on **watchtower-common** which needs to be installed prior to it.

```
git clone https://github.com/icclab/watchtower-automation.git
cd watchtower-automation
mvn clean package
```

### Installation

The easies way to install `watchtower-automation` is to install the `deb` package:

```
sudo dpkg -i target/watchtower-automation-{version}.deb
```

For those which want to manually do everything, they can use the generated `jar`.

### Configuration

`watchtower-automation` comes with a sample configuration file which, after installation, is located in `/etc/watchtower/`. Best way is to work your way from the provided sample:

```
sudo cp /etc/watchtower/automation-config.yml-sample /etc/watchtower/automation-config.yml
```

### Running

The `deb` file contains service files for `Upstart` so you can just do:

```
sudo service watchtower-automation start
```

Please note that the service does not automatically start after `deb` installation.
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

# Author Information

For further information or assistance please contact [**Victor Ion Munteanu**](https://github.com/nemros).
