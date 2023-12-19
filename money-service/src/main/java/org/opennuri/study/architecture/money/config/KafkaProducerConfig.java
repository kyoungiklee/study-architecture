package org.opennuri.study.architecture.money.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.opennuri.study.architecture.common.task.RechargingMoneyTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, RechargingMoneyTask> factory() {
        log.info("producerConfig");

        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonSerializer");

        return new DefaultKafkaProducerFactory<>(producerConfig);
    }

    @Bean
    public org.springframework.kafka.core.KafkaTemplate<String, RechargingMoneyTask> kafkaTemplate() {
        return new KafkaTemplate<>(factory());
    }
}
