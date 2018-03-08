package me.hecun.shipdata.controller;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.model.FMSData;
import me.hecun.shipdata.model.FileResult;
import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.service.BatchJobService;
import me.hecun.shipdata.service.FMSDataService;
import me.hecun.shipdata.service.ParseDataFileService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * 上传 FMS Data 批处理任务
 * @author hecun
 * @date 2018/03/08
 */
@RestController
@RequestMapping("/fms")
@Slf4j
public class FMSDataController {

    /**
     * 设置存放上传的文件的本地的目录
     */
    private String fMSDataFolder = System.getProperty("user.dir") + "\\shipdata-service\\src\\main\\resources\\fms_data_files";

    @Autowired
    private BatchJobService batchJobService;

    @Autowired
    private FMSDataService fmsDataService;

    @PostMapping("/upload")
    public ResponseEntity<GeneralResponse> upload(MultipartFile file) throws IOException {
        log.info("start to upload fms data file.");
        //上传时的文件名
        log.info("file name is {}", file.getOriginalFilename());

        //设置存放上传文件的文件名(文件夹 + 文件名)
        String timestamp = Instant.now().toString();
        String localFileName = timestamp + String.valueOf(RandomUtils.nextInt(1000));
        File localFile = new File(fMSDataFolder, localFileName + ".txt");

        //上传文件
        file.transferTo(localFile);

        //获得上传之后的文件全路径 + 文件名
        String fileName = localFile.getAbsolutePath();

        //执行批处理任务
        batchJobService.startFMSBatchJob(fileName);

        GeneralResponse<FileResult> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, null);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/username/date")
    public ResponseEntity<GeneralResponse> findByUsernameAndTestDate(@RequestParam("username") String username,
                                                                     @RequestParam("testDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date testDate) {
        FMSData fmsData = fmsDataService.findByUsernameAndTestDate(username, testDate);

        if (fmsData == null) {
            GeneralResponse<List<FMSData>> generalResponse = new GeneralResponse<>(ResponseEnum.FMSDATA_NOT_EXIST, null);
            return new ResponseEntity<>(generalResponse, HttpStatus.OK);
        }

        GeneralResponse<FMSData> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, fmsData);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
