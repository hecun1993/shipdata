package me.hecun.shipdata.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * mongodb配置
 *
 * @author hecun
 * @date 2017/10/27
 */
@Configuration
@EnableMongoRepositories(basePackages = "me.hecun.shipdata.repository")
public class MongoDbConfig extends AbstractMongoConfiguration {

    @Value("${mongo.url}")
    private String url;

    @Value("${mongo.db}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(url));
    }
}
