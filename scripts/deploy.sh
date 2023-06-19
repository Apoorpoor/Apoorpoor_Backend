##!/bin/bash
#
#ROOT_PATH="/home/ubuntu/apoorapoor"
#JAR="$ROOT_PATH/application-plain.jar"
#
#APP_LOG="$ROOT_PATH/application.log"
#ERROR_LOG="$ROOT_PATH/error.log"
#START_LOG="$ROOT_PATH/start.log"
#PROFILES_ACTIVE="Dspring.profiles.active=dev"
#
#NOW=$(date +%c)
#
#echo "[$NOW] $JAR 복사" >> $START_LOG
#cp $ROOT_PATH/build/libs/Apoorpoor_Backend-0.0.1-SNAPSHOT.jar $JAR
#
#echo "[$NOW] > $JAR 실행" >> $START_LOG
#nohup java -jar -$PROFILES_ACTIVE $JAR > $APP_LOG 2> $ERROR_LOG &
#
#SERVICE_PID=$(pgrep -f $JAR)
#echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> $START_LOG

##!/bin/bash
REPOSITORY=/home/ubuntu/apoorapoor/build/libs/
cd $REPOSITORY

APP_NAME=apoorapoor
JAR_NAME=$(ls $REPOSITORY | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME

sudo chmod +x "$JAR_PATH"

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &