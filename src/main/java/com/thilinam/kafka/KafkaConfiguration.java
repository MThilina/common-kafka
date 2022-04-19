package com.thilinam.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static com.thilinam.common.AppConfig.*;

public class KafkaConfiguration {


    public Properties getKafkaProducerConfigurationProperties(){
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG,APPLICATION_CLIENT_ID);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG,ACKS_CONFIG);
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,IDEMPOTENCE_CONFIG);
        return properties;
    }

    public Properties getKafkaConsumerConfigurationProperties(){
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVER);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG,APPLICATION_CLIENT_ID);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,KAFKA_GROUP_CONSUMER_GROUP);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        return properties;
    }


    public Properties getKafkaAdminProperties(){
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVER);
        return properties;
    }
}
