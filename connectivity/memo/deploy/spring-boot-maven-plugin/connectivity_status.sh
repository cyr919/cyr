#!/bin/bash

####################
# centos 에서 실행
####################

echo "connectivity stats"

ps -ef | grep -w hs_msp_connectivity | grep -v grep
