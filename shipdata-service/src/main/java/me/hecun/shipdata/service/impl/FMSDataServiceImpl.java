package me.hecun.shipdata.service.impl;

import me.hecun.shipdata.model.FMSData;
import me.hecun.shipdata.repository.FMSDataRepository;
import me.hecun.shipdata.service.FMSDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: He Cun
 * @Date: 2018/3/8 10:46
 */
@Service
public class FMSDataServiceImpl implements FMSDataService {

    @Qualifier("FMSDataRepository")
    @Autowired
    private FMSDataRepository fmsDataRepository;

    @Override
    public FMSData findByUsernameAndTestDate(String username, Date testDate) {
        return fmsDataRepository.findByUsernameAndTestDate(username, testDate);
    }
}
