package me.hecun.shipdata.controller;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;

/**
 * 文件上传和下载
 *
 *
 * @author hecun
 * @date 2017/10/26
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    /**
     * 设置存放上传的文件的本地的目录
     */
    private String folder = "/Users/hecun/JavaProjects/work/fudan/code/shipdata/shipdata-service/src/main/resources/data_files";

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public ResponseEntity<GeneralResponse> upload(MultipartFile file) throws IOException {
        //上传时的文件名
        log.info("file name is {}", file.getOriginalFilename());

        //设置存放上传文件的文件名(文件夹 + 文件名)
        String timestamp = Instant.now().toString();
        String localFileName = timestamp + String.valueOf(RandomUtils.nextInt(1000));
        File localFile = new File(folder, localFileName + ".txt");

        //上传文件
        file.transferTo(localFile);

        String filePath = localFile.getAbsolutePath();
        GeneralResponse<String> generalResponse = new GeneralResponse<>(ResponseEnum.SUCCESS, filePath);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    /**
     * 文件下载
     * @param id
     * @param response
     * @throws Exception
     */
    @GetMapping("/{id}")
    public void download(@PathVariable String id,
                         HttpServletResponse response) throws Exception {
        //jdk7新语法
        //从服务器上获得文件的输入流, 文件名由controller参数传入
        //从response中获得输出流, 写到响应中
        try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
             OutputStream outputStream = response.getOutputStream()) {

            //设置response的属性, 变为下载文件
            response.setContentType("application/x-download");
            //定义下载时的文件名
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            //从输入流拷贝文件到输出流
            IOUtils.copy(inputStream, outputStream);
            //flush
            outputStream.flush();
        }
    }
}
