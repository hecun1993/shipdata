package test

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * spark streaming处理socket数据
  * @author cun.he
  */
object _00_NetworkWordCount {

    def main(args: Array[String]): Unit = {

        if (args.length < 2) {
            System.err.println("Usage: NetworkWordCount <hostname> <port>")
            System.exit(1)
        }

        val sparkConf = new SparkConf().setAppName("_00_NetworkWordCount")
        val ssc = new StreamingContext(sparkConf, Seconds(1))

        val lines = ssc.socketTextStream(args(0), args(1).toInt)
        val words = lines.flatMap(_.split(" "))
        val wordsCount = words.map(x => (x, 1)).reduceByKey(_ + _)

        wordsCount.print()
        ssc.start()
        ssc.awaitTermination()
    }
}
