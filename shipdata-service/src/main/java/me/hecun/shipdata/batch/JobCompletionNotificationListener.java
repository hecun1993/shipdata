package me.hecun.shipdata.batch;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.model.MonitorData;
import me.hecun.shipdata.repository.MonitorDataRepository;
import me.hecun.shipdata.util.FileCheckUtil;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hecun
 */
@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Autowired
    private MonitorDataRepository monitorDataRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("monitor data start .....");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("monitor data end .....");

        JobParameters jobParameters = jobExecution.getJobParameters();
        String roundId = jobParameters.getString("roundId");
        log.info(roundId);

        String shipNumber = jobParameters.getString("shipNumber");
        log.info(shipNumber);

        String fileName = jobParameters.getString("fileName");
        log.info(fileName);

        Integer totalLines = FileCheckUtil.getTotalLines(fileName);
        log.info("totalLines is {}", totalLines);

        List<MonitorData> monitorDataList = monitorDataRepository.findLatestMonitorDatas(totalLines);
        monitorDataList.forEach(monitorData -> {
            monitorData.setRoundId(roundId);
            monitorData.setShipNumber(shipNumber);
            monitorDataRepository.save(monitorData);
        });
    }
}