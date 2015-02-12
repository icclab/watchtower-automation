# Overview
`watchtower-automation` is a component of `watchtower` which handles communication with automation engines. It consumes commands from the Kafka commands queue and it runs them against the automation engines.

Currently, it only supports Rundeck for job execution.

### Build Instructions

The best way to install `watchtower-automation` is to download and build it with Maven. Please not it depends on `watchtower-common` which needs to be installed prior to it.

```
git clone https://github.com/icclab/watchtower-automation.git
cd watchtower-automation
mvn clean install
```

Besides the `jar` file, an additional output is the `deb` package which can be easility installed on Debian based linux. It will add a `watchtower-automation` service, however it first needs to be configured by editing and renaming ```watchtower-automation-config.yml-sample``` in `/etc/watchtower`.