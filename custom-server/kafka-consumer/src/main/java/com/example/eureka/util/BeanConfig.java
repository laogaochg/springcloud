package com.example.eureka.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

import java.net.UnknownHostException;
import java.util.Date;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/6/7 9:59
 */
@Configuration
@EnableKafka
public class BeanConfig {
    @Value("${mongodb.connectOutTime}")
    private String connectOutTime;

    /**
     * kafka批量处理工厂
     */
    @Bean
    public KafkaListenerContainerFactory<?> batchFactory(ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);//设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);//设置提交偏移量的方式
        return factory;
    }


    @Bean
    public MongoClient mongo(MongoProperties properties, Environment environment) throws UnknownHostException {
        Integer time = Integer.valueOf(connectOutTime);
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.threadsAllowedToBlockForConnectionMultiplier(50);
        builder.maxWaitTime(time * 1000);
        builder.connectTimeout(time * 1000);
        builder.readPreference(ReadPreference.primaryPreferred());
        MongoClientOptions build = builder.build();
        MongoClient mongo = properties.createMongoClient(build, environment);
        return mongo;
    }


    /**
     * mongodb配置
     */
    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoClient mongoClient) throws Exception {
        mongoClient.getMongoClientOptions();
        //        MongoClientOptions
        //remove _class
        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        //注册时间转换器
        LongDateConvertor result = new LongDateConvertor();
        GenericConversionService service = (GenericConversionService) converter.getConversionService();
        service.addConverter(result);
        return mongoTemplate;
    }

    public static class LongDateConvertor implements Converter<Long, Date> {
        @Override
        public Date convert(Long aLong) {
            if (aLong == null) return null;
            return new Date(aLong);
        }
    }

}
