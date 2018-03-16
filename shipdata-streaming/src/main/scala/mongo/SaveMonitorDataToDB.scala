package mongo

import com.mongodb.MongoClientOptions.Builder
import com.mongodb.{DB, MongoClient, MongoClientOptions}
import model.MonitorData
import com.mongodb.casbah.commons.{Imports, MongoDBObject}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


object SaveMonitorDataToDB {

  def createConnect(host: String, dataBaseName: String): DB = {
    val builder: Builder = MongoClientOptions.builder()
    builder.connectionsPerHost(500)
    builder.connectTimeout(1000 * 60 * 10)
    builder.maxWaitTime(100 * 60 * 5)

    val options: MongoClientOptions = builder.build()
    var db: DB = null
    var mongo: MongoClient = null

    if (host.isEmpty) {
      mongo = new MongoClient("127.0.0.1", options)
    } else {
      mongo = new MongoClient(host, options)
    }

    if (dataBaseName.isEmpty) {
      db = mongo.getDB("shipdata_realtime")
    } else {
      db = mongo.getDB(dataBaseName)
    }

    db
  }

  def main(args: Array[String]): Unit = {

    if (args.length != 4) {
      //最后一个参数是线程数量
      println("Usage: SaveMonitorDataToDB <zkQuorum> <group> <topics> <numThreads>")
      System.exit(1)
    }

    //用数组接收传入的参数
    val Array(zkQuorum, groupId, topics, numThreads) = args

    val sparkConf = new SparkConf().setAppName("SaveMonitorDataToDB")
      .setMaster("local[2]")

    val sparkSteamingContext = new StreamingContext(sparkConf, Seconds(60))
    //处理传入的topic, 以逗号分隔, 转成map
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap

    //通过Receiver方式从spark接收来自kafka的数据
    val datas = KafkaUtils.createStream(sparkSteamingContext, zkQuorum, groupId, topicMap).map(_._2)


    val log = datas.map(line => {
      if (line.contains("Boat") || line.contains("Data")) {
        print("invalid line...")
      } else {
        val newLine = line.substring(1, line.length - 1)
        val iterms: Array[String] = newLine.split(",")

        MonitorData(iterms(0),
          iterms(1),
          iterms(2),
          iterms(3),
          iterms(4),
          iterms(5),
          iterms(6),
          iterms(7),
          iterms(8),
          iterms(9),
          iterms(10),
          iterms(11),
          iterms(12),
          iterms(13),
          iterms(14),
          iterms(15)
        )
        print("the monitor data is ", MonitorData)

        val db = createConnect("127.0.0.1", "shipdata")
        val dBObject: Imports.DBObject = MongoDBObject(
          "date" -> iterms(0),
          "time" -> iterms(1),
          "isGPS" -> iterms(2),
          "dimensionFlag" -> iterms(3),
          "dimension" -> iterms(4),
          "longitudeFlag" -> iterms(5),
          "longitude" -> iterms(6),
          "shipSpeed" -> iterms(7),
          "shipDirection" -> iterms(8),
          "isWindDataValid" -> iterms(9),
          "realWindSpeed" -> iterms(10),
          "realWindDirection" -> iterms(11),
          "windDirection" -> iterms(12),
          "sidewardsInclination" -> iterms(13),
          "forwardDipAngle" -> iterms(14),
          "vmg" -> iterms(15)
        )

        db.getCollection("vmg_real_data").save(dBObject)
      }
    })

    sparkSteamingContext.start()
    sparkSteamingContext.awaitTermination()
  }
}
