package me.hecun.shipdata.service;

/**
 * Created by hecun on 2017/10/27.
 */
public interface BatchJobService {

    //开始批处理任务
    void startBatchJob(String fileName, String shipNumber, String roundId);
}
