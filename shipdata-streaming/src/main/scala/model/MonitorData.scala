package model

/**
  * @author cun.he
  * 2017-12-12 下午7:12
  */
case class MonitorData(
                      date: String,
                      time: String,
                      isGPS: String,
                      dimensionFlag: String,
                      dimension: String,
                      longitudeFlag: String,
                      longitude: String,
                      shipSpeed: String,
                      shipDirection: String,
                      isWindDataValid: String,
                      realWindSpeed: String,
                      realWindDirection: String,
                      windDirection: String,
                      sidewardsInclination: String,
                      forwardDipAngle: String,
                      vmg: String
                      )