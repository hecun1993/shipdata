package me.hecun.shipdata.repository;

import me.hecun.shipdata.model.FMSData;
import org.springframework.data.repository.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: He Cun
 * @Date: 2018/3/8 10:33
 */
public interface FMSDataRepository extends Repository<FMSData, String> {

    void save(FMSData fmsData);

    FMSData findByUsernameAndTestDate(String username, String testDate);

    List<FMSData> findLatestFMSData();

    List<FMSData> findAll();
}
