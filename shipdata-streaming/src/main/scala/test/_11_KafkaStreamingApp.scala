package test

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Spark Streaming对接Kafka
  */
object _11_KafkaStreamingApp {

    def main(args: Array[String]): Unit = {

        if (args.length != 4) {
            System.err.println("Usage: _11_KafkaStreamingApp <zkQuorum> <group> <topics> <numThreads>")
        }

        val Array(zkQuorum, group, topics, numThreads) = args

        val sparkConf = new SparkConf().setAppName("_11_KafkaStreamingApp").setMaster("local[2]")
        val ssc = new StreamingContext(sparkConf, Seconds(5))

        val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap

        val messages = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)

        messages.map(_._2).count().print()

        ssc.start()
        ssc.awaitTermination()
    }
}
