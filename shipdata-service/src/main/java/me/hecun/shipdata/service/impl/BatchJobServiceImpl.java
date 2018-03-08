package me.hecun.shipdata.service.impl;

import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.BatchJobException;
import me.hecun.shipdata.service.BatchJobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 *
 * @author hecun
 * @date 2017/10/27
 */
@Service
public class BatchJobServiceImpl implements BatchJobService {

    @Autowired
    private FlatFileItemReader fileItemReader;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importMonitorDataJob;

    //=====================================================

    @Autowired
    private FlatFileItemReader fmsFileItemReader;

    @Autowired
    private Job importFMSDataJob;

    @Override
    public void startBatchJob(String fileName, String shipNumber, String roundId) {

        //要解析的文件资源
        FileSystemResource fileSystemResource = new FileSystemResource(new File(fileName));
        fileItemReader.setResource(fileSystemResource);

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("roundId", roundId)
                    .addString("shipNumber", shipNumber)
                    .addString("fileName", fileName)
                    .addDate("date", new Date())
                    .toJobParameters();
            jobLauncher.run(importMonitorDataJob, jobParameters);
        } catch (Exception e) {
            throw new BatchJobException(ResponseEnum.BATCH_JOB_ERROR);
        }
    }

    @Override
    public void startFMSBatchJob(String fileName) {
        FileSystemResource fileSystemResource = new FileSystemResource(new File(fileName));
        fmsFileItemReader.setResource(fileSystemResource);

        try {
            JobParameters jobParameters = new JobParameters();
            jobLauncher.run(importFMSDataJob, jobParameters);
        } catch (Exception e) {
            throw new BatchJobException(ResponseEnum.BATCH_JOB_ERROR);
        }
    }
}
