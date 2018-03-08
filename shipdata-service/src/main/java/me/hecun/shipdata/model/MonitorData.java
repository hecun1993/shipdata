package me.hecun.shipdata.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * $X（真风数据是否有效），XX.X（真风风速），XXX.X（真风风向），XXX.X（风向角），ZXX.X（左右倾角，R为右倾，L为左倾，Q为迎风情况下的倾斜，F为迎风情况下的反扣），ZXX.X（前后倾角），ZXX.X（VMG）*
 *
 * @author hecun
 * @date 2017/10/26
 */
@Data
@Document(collection = "vmg_data")
public class MonitorData {

    @Id
    private String id;

    //监控轮次
    private String roundId;

    //帆船的编号
    private String shipNumber;

    //记录创建时间
    private Long createTime;

    //日期
    private String date;

    //时间
    private String time;

    //是否GPS已校准
    private String isGPS;

    //南北纬
    private String dimensionFlag;

    //纬度
    private String dimension;

    //东西经
    private String longitudeFlag;

    //经度
    private String longitude;

    //船速
    private String shipSpeed;

    //航向
    private String shipDirection;

    //真风数据是否有效
    private String isWindDataValid;

    //真风风速
    private String realWindSpeed;

    //真风风向
    private String realWindDirection;

    //风向角
    private String windDirection;

    //左右倾角(R为右倾，L为左倾，Q为迎风情况下的倾斜，F为迎风情况下的反扣)
    private String sidewardsInclination;

    //前后倾角
    private String forwardDipAngle;

    //VMG
    private String vmg;
}
