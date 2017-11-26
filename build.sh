#!/usr/bin/env bash

mvn clean
mvn package
java -jar target/simpleCache.jar -capacity 3 -strategy LFU