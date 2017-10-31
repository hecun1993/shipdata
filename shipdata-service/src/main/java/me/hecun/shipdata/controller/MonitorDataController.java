package me.hecun.shipdata.controller;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.model.FileResult;
import me.hecun.shipdata.model.MonitorData;
import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.service.BatchJobService;
import me.hecun.shipdata.service.MonitorDataService;
import me.hecun.shipdata.service.ParseDataFileService;
import me.hecun.shipdata.util.UuidUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * Created by hecun on 2017/10/28.
 */
@RestController
@RequestMapping("/monitor")
@Slf4j
public class MonitorDataController {

    //设置存放上传的文件的本地的目录
    private String folder = "/Users/hecun/JavaProjects/work/fudan/code/shipdata/shipdata-service/src/main/resources/data_files";

    @Autowired
    private ParseDataFileService parseDataFileService;

    @Autowired
    private BatchJobService batchJobService;

    @Autowired
    private MonitorDataService monitorDataService;

    @PostMapping("/upload")
    public ResponseEntity<GeneralResponse> upload(MultipartFile file) throws IOException {
        //上传时的文件名
        log.info("file name is {}", file.getOriginalFilename());

        //设置存放上传文件的文件名(文件夹 + 文件名)
        String timestamp = Instant.now().toString();
        String localFileName = timestamp + String.valueOf(RandomUtils.nextInt(1000));
        File localFile = new File(folder, localFileName + ".txt");

        //上传文件
        file.transferTo(localFile);

        //获得上传之后的文件全路径 + 文件名
        String fileName = localFile.getAbsolutePath();
        //帆船名字
        String shipNumber = parseDataFileService.parseDataFile(fileName);
        //轮次
        String roundId = UuidUtil.genShortUuid();

        //执行批处理任务
        batchJobService.startBatchJob(fileName, shipNumber, roundId);

        FileResult fileResult = new FileResult(shipNumber, roundId);

        GeneralResponse<FileResult> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, fileResult);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/data")
    public ResponseEntity<GeneralResponse> findByRoundId(@RequestParam("roundId") String roundId) {
        List<MonitorData> monitorDataList = monitorDataService.findByRoundId(roundId);
        GeneralResponse<List<MonitorData>> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, monitorDataList);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
