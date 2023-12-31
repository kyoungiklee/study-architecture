package org.opennuri.study.architecture.logging;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Properties;

import static java.util.Collections.singletonList;

@Slf4j
@Component
public class LoggingConsumer {
    private final KafkaConsumer<String, String> kafkaConsumer;

    public LoggingConsumer(@Value("${kafka_clusters_bootstrapservers}") String bootstrapServers,
                           @Value("${kafka.task.logging_topic}") String topic) {


        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("group.id", "logging-consumer");
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        this.kafkaConsumer = new KafkaConsumer<>(properties);
        this.kafkaConsumer.subscribe(singletonList(topic));

        log.info("LoggingConsumer is created");

        // Thread를 이용하여 KafkaConsumer를 실행한다.
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    kafkaConsumer.poll(Duration.ofMillis(1000)).forEach(record -> {
                        log.info("Received log: {}", record);
                    });
                }
            } catch (Exception e) {
                log.error("Something bad happened {}", e.getMessage());
            } finally {
                kafkaConsumer.close();
            }
        });
        consumerThread.start();
    }
}
