package me.hecun.shipdata.service;

import me.hecun.shipdata.model.MonitorData;

import java.util.List;

/**
 *
 * @author hecun
 * @date 2017/10/28
 */
public interface MonitorDataService {

    List<MonitorData> findByRoundId(String roundId);

    List<MonitorData> findByRoundIdAndShipNumberAndDate(String roundId, String shipNumber, String date);
}
