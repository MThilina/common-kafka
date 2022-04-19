package com.thilinam.kafka;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.thilinam.common.AppConfig.DEFAULT_PARTITION_COUNT;
import static com.thilinam.common.AppConfig.DEFAULT_REPLICATION_FACTOR;

/**
 * <p>
 *     This class create a singleton kafka producer to produce records to the kafka broker topics
 * </p>
 */
public class KafkaProducerDetail {

    private static KafkaProducerDetail details;

    private  KafkaConfiguration config = new KafkaConfiguration();

    private final Producer<String, String> producer = new KafkaProducer<>(
            config.getKafkaProducerConfigurationProperties()); // single time created kafka producer

    private final Admin admin=  Admin.create(
            config.getKafkaAdminProperties()); // single time creation of kafka admin

    private static Logger logger = LoggerFactory.getLogger(KafkaProducerDetail.class);



    private KafkaProducerDetail() {}

    public static KafkaProducerDetail getProducerInstance(){
        if(Objects.isNull(details)){
            details = new KafkaProducerDetail();
        }
        return details;
    }

    /**
     * <p>
     *     This method write the message to the kafka topic
     * </p>
     * @param topic
     * @param payload
     */
    public  void produceRecord(String topic,String payload){
        validateTopic(topic); // check topic existing in the kafka brokers if not create
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,payload);
        try{
            producer.send(record);
            producer.flush();
        }catch (Exception e){
            producer.abortTransaction(); // abort transaction if failed
            producer.close(); //closes the single producer if exception throws
            logger.error("error occurred when writing to kafka topic :"+topic+" error:"+e.getMessage());
        }
    }

    /**
     * <p>
     *     This method will create a topic on default setting if the topic is does not appear in the broker
     * </p>
     * @param topicName
     */
    public void validateTopic(String topicName){
        Set<String> kafkaTopics = this.listDownTopics();
        if
        (Objects.isNull(kafkaTopics)|| !kafkaTopics.contains(topicName) || kafkaTopics.isEmpty()){
            try{
                NewTopic topic = new NewTopic(topicName, DEFAULT_PARTITION_COUNT, (short) DEFAULT_REPLICATION_FACTOR);
                admin.createTopics(Collections.singleton(topic));
            }catch (Exception e){
                logger.error("error occurred while trying to create topic :"+topicName+" error :"+e.getMessage());
                admin.close(); // close single admin object if error occurred
            }

        }
        return;
    }

    /************************************ Private Methods **************************************************/

    /**
     * <p>
     *     This method will list-down topics which are available in the kafka broker
     * </p>
     * @return
     */
    private Set<String> listDownTopics(){
        Set<String> kafkaTopics = null;

        try {
            kafkaTopics = admin.listTopics().names().get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("error occurred when listing kafka topics:"+e.getMessage());
            admin.close(); // closes the single admin object
        }
        return kafkaTopics;
    }
}
