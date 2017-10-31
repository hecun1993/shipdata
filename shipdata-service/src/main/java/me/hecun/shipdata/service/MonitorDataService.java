package me.hecun.shipdata.service;

import me.hecun.shipdata.model.MonitorData;

import java.util.List;

/**
 * Created by hecun on 2017/10/28.
 */
public interface MonitorDataService {
    List<MonitorData> findByRoundId(String roundId);
}
