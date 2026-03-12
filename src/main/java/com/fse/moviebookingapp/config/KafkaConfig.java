package com.fse.moviebookingapp.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.group-id}")
    private String groupId;
    @Value("${kafka.truststore-location}")
    private String TRUSTSTORE_LOCATION;
    @Value("${kafka.truststore-password}")
    private String TRUSTSTORE_PASSWORD;
    @Value("${kafka.keystore-location}")
    private String KEYSTORE_LOCATION;
    @Value("${kafka.keystore-password}")
    private String KEYSTORE_PASSWORD;
    @Value("${kafka.key-password}")
    private String KEY_PASSWORD;
    @Value("${kafka.sasl-username}")
    private String sasl_username;
    @Value("${kafka.sasl-password}")
    private String sasl_password;


    @Bean
    public ConsumerFactory<String, Object> consumerFactory(){
        Map<String,Object>  props =new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username='%s' password='%s';";
        String jaasConfig = String.format(jaasTemplate, sasl_username, sasl_password);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        props.put("sasl.jaas.config", jaasConfig);
        props.put("ssl.endpoint.identification.algorithm", "");
        props.put("ssl.truststore.location", TRUSTSTORE_LOCATION);
        props.put("ssl.truststore.password", TRUSTSTORE_PASSWORD);
        props.put("ssl.keystore.type", "PKCS12");
        props.put("ssl.keystore.location", KEYSTORE_LOCATION);
        props.put("ssl.keystore.password", KEYSTORE_PASSWORD);
        props.put("ssl.key.password", KEY_PASSWORD);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory(){
        Map<String, Object> configProps =  new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put("security.protocol", "SASL_SSL");
        configProps.put("sasl.mechanism", "SCRAM-SHA-256");
        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username='%s' password='%s';";
        String jaasConfig = String.format(jaasTemplate, sasl_username, sasl_password);
        configProps.put("sasl.jaas.config", jaasConfig);
        configProps.put("ssl.endpoint.identification.algorithm", "");
        configProps.put("ssl.truststore.location", TRUSTSTORE_LOCATION);
        configProps.put("ssl.truststore.password", TRUSTSTORE_PASSWORD);
        configProps.put("ssl.keystore.type", "PKCS12");
        configProps.put("ssl.keystore.location", KEYSTORE_LOCATION);
        configProps.put("ssl.keystore.password", KEYSTORE_PASSWORD);
        configProps.put("ssl.key.password", KEY_PASSWORD);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
