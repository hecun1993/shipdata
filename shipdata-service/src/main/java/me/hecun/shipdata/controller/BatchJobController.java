package me.hecun.shipdata.controller;

import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.service.BatchJobService;
import me.hecun.shipdata.service.ParseDataFileService;
import me.hecun.shipdata.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hecun on 2017/10/27.
 */
@RestController
@RequestMapping("/job")
public class BatchJobController {

    @Autowired
    private ParseDataFileService parseDataFileService;

    @Autowired
    private BatchJobService batchJobService;

    @GetMapping("/start")
    public ResponseEntity<GeneralResponse> startBatchJob(@RequestParam("fileName") String fileName) {

        //帆船名字
        String shipNumber = parseDataFileService.parseDataFile(fileName);
        //轮次
        String roundId = UuidUtil.genShortUuid();
        batchJobService.startBatchJob(fileName, shipNumber, roundId);

        GeneralResponse<Object> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, null);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
