package test

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 使用Spark Streaming处理文件系统(local/hdfs)的数据
  */
object _02_FileWordCount {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local").setAppName("_02_FileWordCount")
        val ssc = new StreamingContext(sparkConf, Seconds(5))

        //监控这个文件夹, 必须有moving操作时, 才能监控到数据
        val lines = ssc.textFileStream("file:///Users/hecun/JavaProjects/work/fudan/code/spark-streaming/")

        val result = lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
        result.print()

        ssc.start()
        ssc.awaitTermination()

    }

}
