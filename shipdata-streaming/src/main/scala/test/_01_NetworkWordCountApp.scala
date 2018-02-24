package test

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author cun.he
  */
object _01_NetworkWordCountApp {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setAppName("_01_NetworkWordCountApp").setMaster("local[2]")
        val ssc = new StreamingContext(sparkConf, Seconds(1))

        val lines = ssc.socketTextStream("hadoop01", 6789)
        val words = lines.flatMap(_.split(" "))
        val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)

        wordCounts.print()
        ssc.start()
        ssc.awaitTermination()
    }
}
