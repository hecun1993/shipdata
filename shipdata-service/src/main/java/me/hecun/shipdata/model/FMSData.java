package me.hecun.shipdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author: He Cun
 * @Date: 2018/3/8 10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "fms_data")
public class FMSData {

    @Id
    private String id;

    /**
     * 记录创建时间
     */
    private Long createTime;

    /**
     * 测试时间
     */
    private Date testDate;

    /**
     * 运动员的名字
     */
    private String username;

    /**
     * 深蹲
     */
    private Double deepSquat;

    /**
     * 跨栏架
     */
    private Double hurdleStep;

    /**
     * 直线弓箭步蹲
     */
    private Double inLineLunge;

    /**
     * 肩膀灵活性
     */
    private Double shoulderMobility;

    /**
     * 直腿主动上抬
     */
    private Double activeStraightLegRaise;

    /**
     * 控体俯卧撑
     */
    private Double trunkStability;

    /**
     * 转体稳定性
     */
    private Double rotaryStabilityQuadruped;

    /**
     * 个人总分
     */
    private Double totalScore;
}
