package org.opennuri.study.architecture.common;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class LoggingProducer {

    private final KafkaProducer<String, String> kafkaProducer;
    private final String topic;

    public LoggingProducer(@Value("${logging.topic}") String topic,
                           @Value("${kafka.bootstrap.servers}") String bootstrapServers) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        this.kafkaProducer = new KafkaProducer<>(properties);
        this.topic = topic;
        log.info("LoggingProducer is created");
    }
    public void sendLog(String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        log.info("Sending log: {}", record);

        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception == null) {
                log.info("The offset of the record we just sent is: " + metadata.offset());
            } else {
                log.error("Something bad happened {}", exception.getMessage());
            }
        });
        log.info("Log is sent");
    }
}
