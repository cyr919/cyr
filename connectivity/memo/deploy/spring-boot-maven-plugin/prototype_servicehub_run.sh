#!/bin/bash

####################
# ubuntu 에서 실행
####################

echo "prototype :: $0 :: " $0 
echo "prototype :: $1 :: " $1 

echo "prototype :: 전체 변수 :: " $* 

nohup java -cp connectivity-0.0.1-SNAPSHOT.jar -Dloader.main=com.prototype.servicehub.ServicehubMainRun org.springframework.boot.loader.PropertiesLauncher $* > /dev/null 2>&1&

echo "prototype run"


