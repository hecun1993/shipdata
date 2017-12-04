package me.hecun.shipdata.controller;

import me.hecun.shipdata.model.Ship;
import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author cun.he
 * @date 2017-11-30 下午10:14
 */
@RestController
@RequestMapping("/ship")
public class ShipController {

    @Autowired
    private ShipService shipService;

    /**
     * 根据帆船编号查询帆船信息
     */
    @GetMapping
    public ResponseEntity<GeneralResponse> ship(@RequestParam("shipNumber") String shipNumber) {
        Ship ship = shipService.findByShipNumber(shipNumber);

        GeneralResponse<Ship> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, ship);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    /**
     * 添加帆船数据
     */
    @PostMapping("/add")
    public ResponseEntity<GeneralResponse> ship(Ship ship) {
        shipService.addShip(ship);

        GeneralResponse<Ship> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, ship);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    /**
     * 查询所有的帆船信息
     */
    @GetMapping("/all")
    public ResponseEntity<GeneralResponse> ship() {
        List<Ship> shipList = shipService.findAllShip();

        GeneralResponse<List<Ship>> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, shipList);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
