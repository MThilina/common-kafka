package com.thilinam.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Objects;

/**
 * <p>
 *     This class creates a singleton Kafka consumer which will be subscribe and listen to the given topic
 * </p>
 */
public class KafkaConsumerDetail {

    private KafkaConsumerDetail(){}

    private static KafkaConsumerDetail kafkaConsumerDetail;

    KafkaConfiguration configuration = new KafkaConfiguration();

    private final Consumer<String, String> consumer =
            new KafkaConsumer<>(configuration.getKafkaConsumerConfigurationProperties()); //single consumer

    public static KafkaConsumerDetail getKafkaConsumerInstance(){
        if(Objects.isNull(kafkaConsumerDetail)){
            kafkaConsumerDetail = new KafkaConsumerDetail();
        }
        return kafkaConsumerDetail;
    }

    /**
     * <p>
     *     This method subscribe to the consumer topic and returns the subscribed consumer
     * </p>
     * @param topic
     * @return
     */
    public Consumer subscribeConsumer(String topic){
      consumer.subscribe(Collections.singleton(topic));
      return consumer;
    }

}
