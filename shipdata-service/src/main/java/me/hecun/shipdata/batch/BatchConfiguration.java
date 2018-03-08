package me.hecun.shipdata.batch;

import me.hecun.shipdata.model.FMSData;
import me.hecun.shipdata.model.MonitorData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * spring batch 批处理任务的配置
 *
 *
 * @author hecun
 * @date 2017/10/26
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public FlatFileItemReader<FMSData> fmsFileItemReader() {
        FlatFileItemReader<FMSData> fmsDataFlatFileItemReader = new FlatFileItemReader<>();

        fmsDataFlatFileItemReader.setLineMapper(
                new DefaultLineMapper<FMSData>() {
                    {
                        setLineTokenizer(new DelimitedLineTokenizer(",") {
                            {
                                setNames(new String[] {"deepSquat", "rightHurdleStep", "leftHurdleStep", "rightInLineLunge", "leftInLineLunge", "rightShoulderMobility", "leftShoulderMobility", "rightActiveStraightLegRaise", "leftActiveStraightLegRaise", "trunkStability", "rightRotaryStabilityQuadruped", "leftRotaryStabilityQuadruped", "totalScore"});
                            }
                        });
                        setFieldSetMapper(new BeanWrapperFieldSetMapper<FMSData>() {
                            {
                                setTargetType(FMSData.class);
                            }
                        });
                    }
                }
        );

        return fmsDataFlatFileItemReader;
    }

    public ItemProcessor<FMSData, FMSData> fmsDataItemProcessor() {
        return new FMSDataItemProcess();
    }

    @Bean
    public MongoItemWriter<FMSData> fmsDataMongoItemWriter() {
        MongoItemWriter<FMSData> fmsDataMongoItemWriter = new MongoItemWriter<>();
        fmsDataMongoItemWriter.setTemplate(mongoTemplate);
        fmsDataMongoItemWriter.setCollection("fms_data");
        return fmsDataMongoItemWriter;
    }

    @Bean
    public Job importFMSDataJob(FMSJobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importFMSDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(importFMSDataStep())
                .end()
                .build();
    }

    @Bean
    public Step importFMSDataStep() {
        return stepBuilderFactory.get("importFMSDataStep")
                .<FMSData, FMSData>chunk(100)
                .reader(fmsFileItemReader())
                .processor(fmsDataItemProcessor())
                .writer(fmsDataMongoItemWriter())
                .build();
    }

    //======================================================================

    @Bean
    public FlatFileItemReader<MonitorData> fileItemReader() {

        FlatFileItemReader<MonitorData> fileItemReader = new FlatFileItemReader<>();

        //reader.setResource(new ClassPathResource("data_files/2016071102-01.TXT"));

        fileItemReader.setLineMapper(
                new DefaultLineMapper<MonitorData>() {
                    {
                        setLineTokenizer(new DelimitedLineTokenizer(",") {{
                            setNames(new String[]{"date", "time", "isGPS", "dimensionFlag", "dimension", "longitudeFlag", "longitude", "shipSpeed", "shipDirection", "isWindDataValid", "realWindSpeed", "realWindDirection", "windDirection", "sidewardsInclination", "forwardDipAngle", "vmg"});
                        }
                        });
                        setFieldSetMapper(new BeanWrapperFieldSetMapper<MonitorData>() {{
                            setTargetType(MonitorData.class);
                        }});
                    }});

        return fileItemReader;
    }

    public ItemProcessor<MonitorData, MonitorData> monitorDataItemProcessor() {
        return new MonitorDataItemProcess();
    }

    @Bean
    public MongoItemWriter<MonitorData> mongoItemWriter() {
        MongoItemWriter<MonitorData> mongoItemWriter = new MongoItemWriter<>();
        mongoItemWriter.setTemplate(mongoTemplate);
        mongoItemWriter.setCollection("vmg_data");
        return mongoItemWriter;
    }

    @Bean
    public Job importMonitorDataJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importMonitorDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(importMonitorDataStep())
                .end()
                .build();
    }

    @Bean
    public Step importMonitorDataStep() {
        return stepBuilderFactory.get("importMonitorDataStep")
                .<MonitorData, MonitorData>chunk(100)
                .reader(fileItemReader())
                .processor(monitorDataItemProcessor())
                .writer(mongoItemWriter())
                .build();
    }
}

