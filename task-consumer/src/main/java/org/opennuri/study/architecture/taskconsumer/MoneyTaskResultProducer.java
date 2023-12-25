package org.opennuri.study.architecture.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.opennuri.study.architecture.common.task.RechargingMoneyTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoneyTaskResultProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.task.result.topic}")
    private String topicName;

    public void sendTaskResult(String key, RechargingMoneyTask task) {
        log.info("send: ({}, {})", key, task);

        ObjectMapper mapper = new ObjectMapper();
        String jsonValue;
        try {
            jsonValue = mapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, jsonValue);

        kafkaTemplate.send(record);
    }
}
