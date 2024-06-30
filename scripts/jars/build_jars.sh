#!/bin/bash

mvn -f config-service/pom.xml clean package -Dmaven.test.skip=true
mvn -f quoting-service/pom.xml clean package -Dmaven.test.skip=true