#!/bin/bash

####################
# centos 에서 실행
####################

echo "connectivity run"

cd /hs/mgp/apps/connectivity
echo "cd /hs/mgp/apps/connectivity"

nohup java -Xmx2048m -Dfile.encoding=UTF-8 -Dname=hs_msp_connectivity -cp ./:./lib/*:./connectivity-0.0.1-SNAPSHOT.jar com.prototype.MainRun > /dev/null 2>&1&

echo "java run"
