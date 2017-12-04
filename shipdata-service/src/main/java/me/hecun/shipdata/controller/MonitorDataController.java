package me.hecun.shipdata.controller;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.model.FileResult;
import me.hecun.shipdata.model.MonitorData;
import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.MonitorDataNotExistException;
import me.hecun.shipdata.service.BatchJobService;
import me.hecun.shipdata.service.MonitorDataService;
import me.hecun.shipdata.service.ParseDataFileService;
import me.hecun.shipdata.util.UuidUtil;
import org.apache.commons.lang.StringUtils;
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
 *
 * @author hecun
 * @date 2017/10/28
 */
@RestController
@RequestMapping("/monitor")
@Slf4j
public class MonitorDataController {

    /**
     * 设置存放上传的文件的本地的目录
     */
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

        String originalFileName = file.getOriginalFilename();
        //轮次
        String roundId = StringUtils.substringBetween(originalFileName, "-", ".");

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

        //执行批处理任务
        batchJobService.startBatchJob(fileName, shipNumber, roundId);

        //返回给前端的结果
        FileResult fileResult = new FileResult(shipNumber, roundId);

        GeneralResponse<FileResult> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, fileResult);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/datas")
    public ResponseEntity<GeneralResponse> findByRoundIdAndShipNumberAndDate(@RequestParam("roundId") String roundId,
                                                                             @RequestParam("shipNumber") String shipNumber,
                                                                             @RequestParam("date") String date) {
        List<MonitorData> monitorDataList = monitorDataService.findByRoundIdAndShipNumberAndDate(roundId, shipNumber, date);

        if (monitorDataList.isEmpty()) {
            GeneralResponse<List<MonitorData>> generalResponse = new GeneralResponse<>(ResponseEnum.MONITORDATA_NOT_EXIST, null);
            return new ResponseEntity<>(generalResponse, HttpStatus.OK);
        }

        GeneralResponse<List<MonitorData>> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, monitorDataList);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
