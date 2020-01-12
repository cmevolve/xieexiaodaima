#!/bin/bash
#jenkins 重启脚本

#删除lok文件
find /home/oracle/Oracle/Middleware/user_projects/domains/hhuat7002/ -name "*.lok" -print -exec rm -rf {} \;
#清除服务器缓存
cd /home/oracle/Oracle/Middleware/user_projects/domains/hhuat7002/servers/AdminServer/tmp/_WL_user
rm -rf pcisv7
#查找并强制杀死进程
ps -ef|egrep 7002|gawk '{print $2}'|while read pid
do
kill -9 $pid
done
#以当前时间命名后台日志，并显示在屏幕上
str=$"/n"
MYDATE=`date +%Y%m%d%H%M%S` 
echo "$MYDATE"
#清除历史日志，防止占内存过多
cd /home/oracle/Oracle/Middleware/user_projects/domains/hhuat7002/logs
rm -rf *.log
cd /home/oracle/Oracle/Middleware/user_projects/domains/hhuat7002/bin
nohup ./startWebLogic.sh>/home/oracle/Oracle/Middleware/user_projects/domains/hhuat7002/logs/$MYDATE.log 2>&1 &
# 重命名jar包
mv /home/source/7002/policy_vhl/policy_vhl.jar  policy_vhl.$MYDATE.jar
#cd /home/oracle/Oracle/Middleware/user_projects/domains/hhuat7002/logs
#睡眠3秒钟，防止进程太快，找不到文件
#sleep 3
#tail -f $MYDATE.log
