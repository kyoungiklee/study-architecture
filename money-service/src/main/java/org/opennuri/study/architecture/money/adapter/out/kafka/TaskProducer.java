package org.opennuri.study.architecture.money.adapter.out.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.opennuri.study.architecture.common.task.RechargingMoneyTask;
import org.opennuri.study.architecture.money.application.port.out.kafka.SendRechargingMoneyTaskPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {

    //private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProducer<String, String> kafkaProducer;

    @Value("${kafka.task.topic}")
    private String topicName;

    public TaskProducer(@Value("${kafka.bootstrap.servers}") String bootstrapServers) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("acks", "all");
        properties.put("retries", 3);
        properties.put("linger.ms", 1);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        this.kafkaProducer = new KafkaProducer<>(properties);

    }

    @Override
    public void sendRechargingMoneyTask(RechargingMoneyTask task) {
        log.info("sendRechargingMoneyTask: {}", task.getTaskId());
        this.sendMassage(task.getTaskId(), task);

    }

    private void sendMassage(String key, RechargingMoneyTask task) {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonStringToProducer = null;

        try {
            //작업요청을 카프카에게 전달하기 위해 json으로 변환
            jsonStringToProducer = objectMapper.writeValueAsString(task);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //카프카에게 작업요청 전달 컨슈머에서 작업 요청을 받아서 처리(비동기 처리)
        this.kafkaProducer.send(
            new ProducerRecord<>(this.topicName, key, jsonStringToProducer),
            (metadata, exception) -> {
                if (exception != null) {
                    log.info("Error sending message with key: {} to topic: {}", key, this.topicName);
                } else {
                    log.info("Message sent with key: {} to topic: {}", key, this.topicName);
                }
            }
        );

        /*ProducerRecord<String, String> record = new ProducerRecord<>(this.topicName, key, jsonStringToProducer);
        CompletableFuture<SendResult<String, String>> send = this.kafkaTemplate.send(record);
        try {
            SendResult<String, String> sendResult = send.get();
            log.info("sendResult: {}", sendResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }
}
