package me.hecun.shipdata.service.impl;

import me.hecun.shipdata.model.Ship;
import me.hecun.shipdata.repository.ShipRepository;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.ShipNotExistException;
import me.hecun.shipdata.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cun.he
 * @date 2017-11-30 下午10:04
 */
@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public Ship findByShipNumber(String shipNumber) {
        Ship ship = shipRepository.findByShipNumber(shipNumber);
        if (ship == null) {
            throw new ShipNotExistException(ResponseEnum.SHIP_NOT_EXIST);
        }
        return ship;
    }

    @Override
    public void addShip(Ship ship) {
        shipRepository.save(ship);
    }

    @Override
    public List<Ship> findAllShip() {
        return shipRepository.findAll();
    }
}
