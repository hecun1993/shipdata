package me.hecun.shipdata.batch;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * spring batch 批处理任务的配置
 *
 * Created by hecun on 2017/10/26.
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

