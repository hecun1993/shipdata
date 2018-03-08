package me.hecun.shipdata.batch;

import me.hecun.shipdata.model.FMSData;
import me.hecun.shipdata.model.MonitorData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 *
 * @author hecun
 * @date 2017/10/28
 */
@Component
public class FMSDataItemProcess implements ItemProcessor<FMSData, FMSData> {

    @Override
    public FMSData process(FMSData fmsData) throws Exception {
        fmsData.setCreateTime(System.currentTimeMillis());
        return fmsData;
    }
}
