#!/bin/env bash

java -Xmx1024m -Xms1024m -Xss256k -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=1024m -Ddatax.home=/data/application/datax -jar /data/application/datax/plugin/web/skycloud-datax-web-0.0.1-SNAPSHOT.jar &
