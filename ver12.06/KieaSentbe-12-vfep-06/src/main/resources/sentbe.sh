#!/bin/sh

HOME=/Users/kang-air
#HOME=/Users/kang-pro
#HOME=/hw01/ibridge

BASE=/KANG/sentbe/20201010

BASE_SRC=/STS/GIT/KieaSentbe
USER=kang-air
#USER=kangmac
#USER=ibridge

SRC_PATH=$HOME$BASE_SRC
PROJ_PATH=$HOME$BASE
PROJ_NAME=KieaSentbe
SNAPSHOT=0.0.1-SNAPSHOT

JAVA=java
JDK_PORT=
JAVA_PROPS='-Dspring.profiles.active=proj-default,proj-air'
JAVA_OPTS='-Xms128m -Xmx128m' 

# -----------------------------------------------------
#
run_vSentbe() {
    echo "------------------[ 0. run_vSentbe ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep vSentbe | awk '{print $2}'`
    if [ "$PID" ]
    then
        echo ">>>>> already running..... pid = $PID"
        return
    fi
    nohup $JAVA $JAVA_OPTS $JAVA_PROPS -jar $PROJ_PATH/$PROJ_NAME-00-vSentbe-06-$SNAPSHOT.jar > /dev/null 2>&1 &
}
kill_vSentbe() {
    echo "------------------[ 0. kill_vSentbe ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep vSentbe | awk '{print $2}'`
    kill -9 $PID
}
build_vSentbe() {
    echo "------------------[ 0. build_vSentbe ]-------------------"
    cd $SRC_PATH/ver00.06/Kiea*
    ./gradlew clean build -x test
    cp build/libs/*.jar $PROJ_PATH
}

# -----------------------------------------------------
#
run_link() {
    echo "------------------[ 2. run_link ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep link | awk '{print $2}'`
    if [ "$PID" ]
    then
        echo ">>>>> already running..... pid = $PID"
        return
    fi
    nohup $JAVA $JAVA_OPTS $JAVA_PROPS -jar $PROJ_PATH/$PROJ_NAME-02-link-05-$SNAPSHOT.jar > /dev/null 2>&1 &
}
kill_link() {
    echo "------------------[ 2. kill_link ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep link | awk '{print $2}'`
    kill -9 $PID
}
build_link() {
    echo "------------------[ 2. build_link ]-------------------"
    cd $SRC_PATH/ver02.05/Kiea*
    ./gradlew clean build -x test
    cp build/libs/*.jar $PROJ_PATH
}

# -----------------------------------------------------
#
run_online() {
    echo "------------------[ 3. run_online ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep online | awk '{print $2}'`
    if [ "$PID" ]
    then
        echo ">>>>> already running..... pid = $PID"
        return
    fi
    nohup $JAVA $JAVA_OPTS $JAVA_PROPS -jar $PROJ_PATH/$PROJ_NAME-03-online-05-$SNAPSHOT.jar > /dev/null 2>&1 &
}
kill_online() {
    echo "------------------[ 3. kill_online ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep online | awk '{print $2}'`
    kill -9 $PID
}
build_online() {
    echo "------------------[ 3. build_online ]-------------------"
    cd $SRC_PATH/ver03.05/Kiea*
    ./gradlew clean build -x test
    cp build/libs/*.jar $PROJ_PATH
}

# -----------------------------------------------------
#
run_mapper() {
    echo "------------------[ 7. run_mapper ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep mapper | awk '{print $2}'`
    if [ "$PID" ]
    then
        echo ">>>>> already running..... pid = $PID"
        return
    fi
    nohup $JAVA $JAVA_OPTS $JAVA_PROPS -jar $PROJ_PATH/$PROJ_NAME-07-mapper-05-$SNAPSHOT.jar > /dev/null 2>&1 &
}
kill_mapper() {
    echo "------------------[ 7. kill_mapper ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep mapper | awk '{print $2}'`
    kill -9 $PID
}
build_mapper() {
    echo "------------------[ 7. build_mapper ]-------------------"
    cd $SRC_PATH/ver07.05/Kiea*
    ./gradlew clean build -x test
    cp build/libs/*.jar $PROJ_PATH
}

# -----------------------------------------------------
#
run_sbs01() {
    echo "------------------[ 9. run_sbs01 ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep sbs01 | awk '{print $2}'`
    if [ "$PID" ]
    then
        echo ">>>>> already running..... pid = $PID"
        return
    fi
    nohup $JAVA $JAVA_OPTS $JAVA_PROPS -jar $PROJ_PATH/$PROJ_NAME-09-sbs01-05-$SNAPSHOT.jar > /dev/null 2>&1 &
}
kill_sbs01() {
    echo "------------------[ 9. kill_sbs01 ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep sbs01 | awk '{print $2}'`
    kill -9 $PID
}
build_sbs01() {
    echo "------------------[ 9. build_sbs01 ]-------------------"
    cd $SRC_PATH/ver09.05/Kiea*
    ./gradlew clean build -x test
    cp build/libs/*.jar $PROJ_PATH
}

# -----------------------------------------------------
#
run_vfep() {
    echo "------------------[ 12. run_vfep ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep vfep | awk '{print $2}'`
    if [ "$PID" ]
    then
        echo ">>>>> already running..... pid = $PID"
        return
    fi
    nohup $JAVA $JAVA_OPTS $JAVA_PROPS -jar $PROJ_PATH/$PROJ_NAME-12-vfep-05-$SNAPSHOT.jar > /dev/null 2>&1 &
}
kill_vfep() {
    echo "------------------[ 12. kill_vfep ]-------------------"
    PID=`ps -ef | grep -v grep | grep $PROJ_NAME | grep vfep | awk '{print $2}'`
    kill -9 $PID
}
build_vfep() {
    echo "------------------[ 12. build_vfep ]-------------------"
    cd $SRC_PATH/ver12.05/Kiea*
    ./gradlew clean build -x test
    cp build/libs/*.jar $PROJ_PATH
}

# -----------------------------------------------------
# -----------------------------------------------------
# -----------------------------------------------------
#
start() {
    echo "------------------[ start ]-------------------"
    run_vSentbe
    echo "Sleeping..."; sleep 2;
    run_link
    echo "Sleeping..."; sleep 2;
    run_online
    echo "Sleeping..."; sleep 2;
    run_mapper
    echo "Sleeping..."; sleep 2;
    run_sbs01
    echo "Sleeping..."; sleep 2;
    run_vfep
}

stop() {
    echo "------------------[ stop ]-------------------"
    kill_vSentbe
    echo "Sleeping..."; sleep 2;
    kill_link
    echo "Sleeping..."; sleep 2;
    kill_online
    echo "Sleeping..."; sleep 2;
    kill_mapper
    echo "Sleeping..."; sleep 2;
    kill_sbs01
    echo "Sleeping..."; sleep 2;
    kill_vfep
}

status() {
    echo "------------------[ status ]-------------------"
    ps -ef | grep -v grep | grep $PROJ_NAME
}

# -----------------------------------------------------
#
build() {
    echo "------------------[ build ]-------------------"
    build_vSentbe
    echo "Sleeping..."; sleep 1;
    build_link
    echo "Sleeping..."; sleep 1;
    build_online
    echo "Sleeping..."; sleep 1;
    build_mapper
    echo "Sleeping..."; sleep 1;
    build_sbs01
    echo "Sleeping..."; sleep 1;
    build_vfep
    echo "Sleeping..."; sleep 1;

    ls -ltr $PROJ_PATH
}

# -----------------------------------------------------
#
check_curl() {
    echo "------------------[ check_curl ]-------------------"
    while ( true )
    do
        curl http://localhost:17092/v0.5/
        date
        echo "---------------------------------------"
        echo "Sleeping..."; sleep 5;
    done
}

# -----------------------------------------------------
case "$1" in
    build)
        build
        ;;
    start | run)
        start
        status
        check_curl
        ;;
    stop | kill)
        stop
        status
        ;;
    status)
        status
        ;;
    restart)
        stop
        echo "Sleeping..."; sleep 2;
        start
        ;;
    curl)
        check_curl
        ;;
    *)
        echo
        echo "USAGE: $0 {start|stop|status|restart|run}"
        echo
        exit 1
        ;;
    
esac

exit 0
