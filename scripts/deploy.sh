BASE_PATH="/home/ubuntu/apoorapoor"
BUILD_PATH=$(ls $BASE_PATH/build/libs/*.jar)
JAR_NAME=$(basename "$BUILD_PATH")
cd $BASE_PATH
APP_NAME="apoorapoor"
JAR_PATH="$BASE_PATH/build/libs/$JAR_NAME"
sudo chmod +x "$JAR_PATH"
CURRENT_PID=$(pgrep -f $APP_NAME)
if [ -z "$CURRENT_PID" ]; then
  echo "> No process to stop."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 "$CURRENT_PID"
  sleep 5
fi
echo "> Deploying $JAR_PATH"
nohup java -jar "$JAR_PATH" > /dev/null 2> /dev/null < /dev/null &
