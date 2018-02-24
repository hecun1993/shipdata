package test

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Spark Streaming对接Kafka的方式一
  * hadoop01:2181 test kafka_streaming_topic 1
  */
object _09_KafkaReceiverWordCount {

    def main(args: Array[String]): Unit = {

        if (args.length != 4) {
            System.err.println("Usage: _09_KafkaReceiverWordCount <zkQuorum> <group> <topics> <numThreads>")
        }

        val Array(zkQuorum, group, topics, numThreads) = args

        //本地模式
        val sparkConf = new SparkConf().setAppName("_09_KafkaReceiverWordCount").setMaster("local[2]")

        //生产模式
        val sparkConfProd = new SparkConf() //.setAppName("KafkaReceiverWordCount").setMaster("local[2]")

        val ssc = new StreamingContext(sparkConf, Seconds(5))

        val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap

        // TODO... Spark Streaming对接Kafka
        val messages = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)

        // TODO... 自己去测试为什么要取第二个
        messages.map(_._2).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()

        ssc.start()
        ssc.awaitTermination()
    }
}
