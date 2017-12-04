package me.hecun.shipdata.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author cun.he
 * @date 2017-11-30 下午9:56
 */
@Data
@Entity
@Table(name = "ship")
public class Ship {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 帆船编号
     */
    private String shipNumber;

    /**
     * 帆船名称
     */
    private String shipName;

    /**
     * 帆船图片
     */
    private String shipImage;
}
