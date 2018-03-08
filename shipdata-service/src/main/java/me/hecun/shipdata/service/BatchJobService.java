package me.hecun.shipdata.service;

import java.util.Date;

/**
 *
 * @author hecun
 * @date 2017/10/27
 */
public interface BatchJobService {

    /**
     * 开始批处理任务
     * @param fileName
     * @param shipNumber
     * @param roundId
     */
    void startBatchJob(String fileName, String shipNumber, String roundId);

    /**
     * 开始FMS data的批处理任务
     * @param fileName
     */
    void startFMSBatchJob(String fileName, String username, String testDate);
}
