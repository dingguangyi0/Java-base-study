source /etc/profile
curDir=$(cd `dirname $0`; pwd)
scriptName=`basename $0`
cd ${curDir}
log_file=4c498371-061b-408e-8e80-3c3bf93b7f66.log
echo "调度作业的日志文件:[${curDir}/${log_file}]"
runtime=`date '+%Y-%m-%d %H:%M:%S'`
echo "作业执行开始,时间[$runtime]"

hive -hiveconf hive.support.concurrency=false  -hiveconf mapred.job.name=hera_11190 -hiveconf mapred.job.queue.name=root.b2b -f /data/service/hera-admin/2021-08-13/6/1628824195601.hive  2>&1|tee -a ${log_file}


if [ ${PIPESTATUS[0]} != 0 ]
then
    runtime=`date '+%Y-%m-%d %H:%M:%S'`
    echo "作业执行失败,时间[$runtime]"
    exit -1
else
    runtime=`date '+%Y-%m-%d %H:%M:%S'`
    #'此处可设置web或FTP服务,如上传日志文件，以达到网页端可查看完成日志功能'
    echo "作业执行成功,时间[$runtime]"
