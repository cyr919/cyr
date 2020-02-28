#!/bin/bash

####################
# centos 에서 실행
####################

echo "connectivity stop"

_LIST=`ps -ef | grep -w hs_msp_connectivity | grep -v grep | awk '{print $2}'`
kill -9 $_LIST
