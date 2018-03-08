package me.hecun.shipdata.batch;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.model.MonitorData;
import me.hecun.shipdata.repository.FMSDataRepository;
import me.hecun.shipdata.repository.MonitorDataRepository;
import me.hecun.shipdata.util.FileCheckUtil;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hecun
 */
@Component
@Slf4j
public class FMSJobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Qualifier("FMSDataRepository")
    @Autowired
    private FMSDataRepository fmsDataRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("fms data start .....");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("fms data end .....");
    }
}