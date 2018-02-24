package test

import java.sql.{Connection, DriverManager}

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 使用Spark Streaming完成词频统计，并将结果写入到MySQL数据库中
  *
  * wordcount
  */
object _04_ForeachRDDApp {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setAppName("_04_ForeachRDDApp").setMaster("local[2]")
        val ssc = new StreamingContext(sparkConf, Seconds(5))

        val lines = ssc.socketTextStream("hadoop01", 6789)

        val result = lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

        //TODO... 将结果写入到MySQL

        //此处仅仅是将统计结果输出到控制台
        result.print()

        result.foreachRDD(rdd => {
            //先把每个rdd分区, 每一个区创建一个mysql连接, 节省资源
            rdd.foreachPartition(partitionOfRecords => {
                //创建连接
                val connection = createConnection()
                partitionOfRecords.foreach(record => {
                    //插入数据库中
                    val sql = "insert into wordcount(word, wordcount) values('" + record._1 + "'," + record._2 + ")"
                    //执行sql语句
                    connection.createStatement().execute(sql)
                })
                //关闭连接
                connection.close()
            })
        })

        ssc.start()
        ssc.awaitTermination()
    }

    /**
      * 获取MySQL的连接
      * create table wordcount ( word varchar(50) not null, wordcount int(5) not null)
      */
    def createConnection(): Connection = {
        Class.forName("com.mysql.jdbc.Driver")
        DriverManager.getConnection("jdbc:mysql://localhost:3306/imooc_spark", "root", "root")
    }

}
