package me.hecun.shipdata.service;

import me.hecun.shipdata.model.Ship;

import java.util.List;

/**
 * @author cun.he
 * @date 2017-11-30 下午10:03
 */
public interface ShipService {

    /**
     * 根据帆船编号查询帆船信息
     */
    Ship findByShipNumber(String shipNumber);

    /**
     * 增加一艘帆船
     */
    void addShip(Ship ship);

    /**
     * 查到所有的帆船
     */
    List<Ship> findAllShip();
}
