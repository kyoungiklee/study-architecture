package org.opennuri.study.architecture.taskconsumer;

import org.opennuri.study.architecture.taskconsumer.config.KafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {KafkaProperties.class})
public class TaskConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskConsumerApplication.class, args);
    }
}