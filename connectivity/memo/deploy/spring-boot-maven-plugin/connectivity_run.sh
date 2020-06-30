#!/bin/bash

####################
# ubuntu 에서 실행
####################

echo "connectivity :: $0 :: " $0 
echo "connectivity :: $1 :: " $1 

echo "connectivity :: 전체 변수 :: " $* 



nohup java -Xmx512m -Dfile.encoding=UTF-8 -Dname=hs_msp_connectivity -jar connectivity-0.0.1-SNAPSHOT.jar $* > /dev/null 2>&1&

echo "connectivity run"


