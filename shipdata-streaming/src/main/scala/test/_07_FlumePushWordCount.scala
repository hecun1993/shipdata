package test

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Spark Streaming整合Flume的第一种方式
  */
object _07_FlumePushWordCount {

    def main(args: Array[String]): Unit = {

        if (args.length != 2) {
            System.err.println("Usage: _07_FlumePushWordCount <hostname> <port>")
            System.exit(1)
        }

        val Array(hostname, port) = args

        //本地测试
        val sparkConf = new SparkConf().setMaster("local[2]").setAppName("_07_FlumePushWordCount")

        //生产测试
        val sparkConfProd = new SparkConf() //.setMaster("local[2]").setAppName("FlumePushWordCount")

        val ssc = new StreamingContext(sparkConf, Seconds(5))

        //TODO... 使用SparkStreaming整合Flume
        //本地测试
        //val flumeStream = FlumeUtils.createStream(ssc, "0.0.0.0", 41414)
        //telnet localhost 44444

        //生产测试
        val flumeStream = FlumeUtils.createStream(ssc, hostname, port.toInt)

        flumeStream.map(x => new String(x.event.getBody.array()).trim)
                .flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()

        ssc.start()
        ssc.awaitTermination()
    }
}
