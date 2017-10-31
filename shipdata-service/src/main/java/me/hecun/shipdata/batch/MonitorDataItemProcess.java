package me.hecun.shipdata.batch;

import me.hecun.shipdata.model.MonitorData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by hecun on 2017/10/28.
 */
@Component
public class MonitorDataItemProcess implements ItemProcessor<MonitorData, MonitorData> {

    @Override
    public MonitorData process(MonitorData monitorData) throws Exception {
        monitorData.setCreateTime(new Date().getTime());
        return monitorData;
    }
}
