package me.hecun.shipdata.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.hecun.shipdata.model.MonitorData;
import me.hecun.shipdata.repository.MonitorDataRepository;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.MonitorDataNotExistException;
import me.hecun.shipdata.service.MonitorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author hecun
 * @date 2017/10/28
 */
@Service
@Slf4j
public class MonitorDataServiceImpl implements MonitorDataService {

    @Autowired
    private MonitorDataRepository monitorDataRepository;

    @Override
    public List<MonitorData> findByRoundId(String roundId) {
        return monitorDataRepository.findByRoundId(roundId);
    }

    @Override
    public List<MonitorData> findByRoundIdAndShipNumberAndDate(String roundId, String shipNumber, String date) {
        return monitorDataRepository.findByRoundIdAndShipNumberAndDate(roundId, shipNumber, date);
    }
}
