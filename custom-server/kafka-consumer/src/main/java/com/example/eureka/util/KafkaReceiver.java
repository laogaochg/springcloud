package com.example.eureka.util;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/6/8 18:47
 */
@Configuration
@EnableKafka
public class KafkaReceiver {
    private static Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    /**
     * 采集信息的消息队列
     */
//    @KafkaListener(topics = {"collection_messsage_opic"})
    /*public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = String.valueOf(kafkaMessage.get());
            logger.info("get Message : " + message.toString());
            CrawlFluxData fluxData = JSON.parseObject(message, CrawlFluxData.class);
            mongoTemplate.insert(fluxData, SystemConstants.COLLECTION_MSG_COLLECTION);
        }
    }*/
    @KafkaListener(id = "listenCollection", topics = {"${collection_messsage_opic}"}, containerFactory = "batchFactory")
    public void listenList(List<ConsumerRecord<?, ?>> recordList, Acknowledgment ack) {
        try {
            BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, "collectionName");
            for (ConsumerRecord record : recordList) {
                Optional<?> kafkaMessage = Optional.fromNullable(record.value());
                if (kafkaMessage.isPresent()) {
                    String message = String.valueOf(kafkaMessage.get());
                    Object parse = JSON.parse(message);
                    ops.insert(parse);
                }
            }
            //循环插完以后批量执行提交一下ok！
            ops.execute();
            /*if (new Random().nextInt(8) % 2 == 0) {
                throw new NullPointerException();
            }*/
            ack.acknowledge();//手动提交偏移量
            logger.info("consume message ,count :  " + recordList.size());
        } catch (Exception e) {
            logger.error("exception :", e);
            logger.error(recordList.toString());
            logger.error("kafka stop consumer !");
            MessageListenerContainer container = kafkaListenerEndpointRegistry.getListenerContainer("listenCollection");
            container.stop();
        }
    }
}
