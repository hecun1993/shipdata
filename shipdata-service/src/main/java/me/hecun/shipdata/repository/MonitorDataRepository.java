package me.hecun.shipdata.repository;

import me.hecun.shipdata.model.MonitorData;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 *
 * @author hecun
 * @date 2017/10/26
 */
public interface MonitorDataRepository extends Repository<MonitorData, String> {

    void save(MonitorData monitorData);

    List<MonitorData> findLatestMonitorDatas(Integer totalLines);

    List<MonitorData> findByRoundIdAndShipNumberAndDate(String roundId, String shipNumber, String date);
}
