package me.hecun.shipdata.repository.impl;

import me.hecun.shipdata.model.MonitorData;
import me.hecun.shipdata.repository.MonitorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *
 * @author hecun
 * @date 2017/10/26
 */
@Repository
public class MonitorDataRepositoryImpl implements MonitorDataRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MonitorData monitorData) {
        mongoTemplate.save(monitorData);
    }

    @Override
    public List<MonitorData> findLatestMonitorDatas(Integer totalLines) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.limit(totalLines);

        return mongoTemplate.find(query, MonitorData.class);
    }

    @Override
    public List<MonitorData> findByRoundId(String roundId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roundId").is(roundId));
        return mongoTemplate.find(query, MonitorData.class);
    }

    @Override
    public List<MonitorData> findByRoundIdAndShipNumberAndDate(String roundId, String shipNumber, String date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roundId").is(roundId).and("shipNumber").is(shipNumber).and("date").is(date));
        return mongoTemplate.find(query, MonitorData.class);
    }
}
