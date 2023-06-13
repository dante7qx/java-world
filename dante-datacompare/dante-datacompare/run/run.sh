#!/bin/bash

nohup java -jar -Dspring.config.location=application.yml dataCompare.jar > run.log 2>&1 &