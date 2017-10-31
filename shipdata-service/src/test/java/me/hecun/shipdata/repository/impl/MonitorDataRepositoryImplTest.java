package me.hecun.shipdata.repository.impl;

import me.hecun.shipdata.model.MonitorData;
import me.hecun.shipdata.repository.MonitorDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hecun on 2017/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MonitorDataRepositoryImplTest {

    @Autowired
    private MonitorDataRepository monitorDataRepository;

    @Test
    public void test() {
        MonitorData monitorData = new MonitorData();
        monitorData.setDate("xxxx");
        monitorDataRepository.save(monitorData);
    }
}