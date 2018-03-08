package me.hecun.shipdata.repository.impl;

import me.hecun.shipdata.model.FMSData;
import me.hecun.shipdata.model.MonitorData;
import me.hecun.shipdata.repository.FMSDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: He Cun
 * @Date: 2018/3/8 10:36
 */
@Repository
public class FMSDataRepositoryImpl implements FMSDataRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(FMSData fmsData) {
        mongoTemplate.save(fmsData);
    }

    @Override
    public FMSData findByUsernameAndTestDate(String username, String testDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username).and("testDate").is(testDate));
        return mongoTemplate.findOne(query, FMSData.class);
    }

    @Override
    public List<FMSData> findLatestFMSData() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.limit(1);

        return mongoTemplate.find(query, FMSData.class);
    }

    @Override
    public List<FMSData> findAll() {
        return mongoTemplate.findAll(FMSData.class);
    }
}
