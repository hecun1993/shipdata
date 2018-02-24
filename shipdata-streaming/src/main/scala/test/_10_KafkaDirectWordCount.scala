package com.imooc.spark


import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Spark Streaming对接Kafka的方式二: Direct
  * hadoop01:9092 kafka_streaming_topic
  */
object _10_KafkaDirectWordCount {

    def main(args: Array[String]): Unit = {

        if (args.length != 2) {
            System.err.println("Usage: _10_KafkaDirectWordCount <brokers> <topics>")
            System.exit(1)
        }

        val Array(brokers, topics) = args

        //本地测试
        val sparkConf = new SparkConf().setAppName("_10_KafkaDirectWordCount").setMaster("local[2]")

        //生产上的测试
        val sparkConfProd = new SparkConf() //.setAppName("KafkaReceiverWordCount")//.setMaster("local[2]")

        val ssc = new StreamingContext(sparkConf, Seconds(5))

        val topicsSet = topics.split(",").toSet
        val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

        // TODO... Spark Streaming如何对接Kafka
        val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
            ssc, kafkaParams, topicsSet
        )

        messages.map(_._2).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()

        ssc.start()
        ssc.awaitTermination()
    }
}
