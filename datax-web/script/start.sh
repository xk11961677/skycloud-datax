#!/bin/env bash

if [ -z "$1" ];then
   echo "缺少环境变量参数，退出"
   exit 1
else
   env="$1"
fi

java -Xmx1024m -Xms1024m -Xss256k -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=1024m -Denv=$env -Ddatax.home=/data/application/datax -jar /data/application/datax/plugin/web/skycloud-datax-web-0.0.1-SNAPSHOT.jar &
