package me.hecun.shipdata.repository;

import me.hecun.shipdata.model.MonitorData;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by hecun on 2017/10/26.
 */
public interface MonitorDataRepository extends Repository<MonitorData, String> {
    void save(MonitorData monitorData);

    List<MonitorData> findLatestMonitorDatas(Integer totalLines);

    List<MonitorData> findByRoundId(String roundId);
}
