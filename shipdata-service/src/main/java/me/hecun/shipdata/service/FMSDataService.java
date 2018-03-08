package me.hecun.shipdata.service;

import me.hecun.shipdata.model.FMSData;

import java.util.Date;

/**
 * @Author: He Cun
 * @Date: 2018/3/8 10:39
 */
public interface FMSDataService {

    FMSData findByUsernameAndTestDate(String username, Date testDate);
}
