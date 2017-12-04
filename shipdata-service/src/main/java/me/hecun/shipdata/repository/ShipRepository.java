package me.hecun.shipdata.repository;

import me.hecun.shipdata.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cun.he
 * @date 2017-11-30 下午9:59
 */
@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {

    /**
     * 根据帆船编号查询帆船
     */
    Ship findByShipNumber(String shipNumber);
}