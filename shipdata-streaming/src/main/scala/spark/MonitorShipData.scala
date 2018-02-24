package spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


//hadoop:2181 test streaming_topic 1
object MonitorShipData {

    def main(args: Array[String]): Unit = {

        //    System.setProperties("hadoop.home.dir", "")

        //通过启动参数设置, 传入4个参数: hadoop:2181 test streamingtopic 1
        if (args.length != 4) {
            println("Usage: MonitorShipData <zkQuorum> <group> <topics> <numThreads>")
            System.exit(1)
        }

        //用数组接收传入的四个启动参数
        val Array(zkQuorum, group, topics, numThreads) = args

        //定义SparkConf
        val sparkConf = new SparkConf().setAppName("MonitorShipData").setMaster("local[2]")

        //数据每分钟产生一次, spark每分钟处理一次
        val sparkSteamingContext = new StreamingContext(sparkConf, Seconds(60))

        //处理传入的topic, 以逗号分隔, 转成map
        val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap

        //通过Receiver方式从spark接收来自kafka的数据
        val datas = KafkaUtils.createStream(sparkSteamingContext, zkQuorum, group, topicMap).map(_._2)

//        datas.map(_._2).count().print()

        datas.count().print

        sparkSteamingContext.start()
        sparkSteamingContext.awaitTermination()
    }

}