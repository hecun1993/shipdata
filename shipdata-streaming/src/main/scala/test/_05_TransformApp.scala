package test

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 黑名单过滤
  */
object _05_TransformApp {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[2]").setAppName("_05_TransformApp")
        val ssc = new StreamingContext(sparkConf, Seconds(5))

        //构建黑名单
        val blacks = List("zs", "ls")
        //parallelize转成rdd
        //(zs: true) (ls: true)
        val blacksRDD = ssc.sparkContext.parallelize(blacks).map(x => (x, true))

        //访问日志如下:
        //20180808,zs
        //20180808,ls
        //20180808,ww
        //变成
        //(zs: <20180808,zs>)
        //(ls: <20180808,ls>)
        //(ww: <20180808,ww>)
        val lines = ssc.socketTextStream("hadoop01", 6789)
        val clicklog = lines.map(x => (x.split(",")(1), x))
                .transform(rdd => {
                    //变成(zs: [<20180808,zs>, <true>])
                    //变成(ls: [<20180808,ls>, <true>])
                    //变成(ww: [<20180808,ww>, <false>])
                    //关联两个RDD
                    rdd.leftOuterJoin(blacksRDD)
                    .filter(x => x._2._2.getOrElse(false) != true)
                    .map(x => x._2._1)
            })

        clicklog.print()

        ssc.start()
        ssc.awaitTermination()
    }
}
