package org.opennuri.study.architecture.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.opennuri.study.architecture.common.task.RechargingMoneyTask;
import org.opennuri.study.architecture.common.task.SubTask;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskConsumer {

    private final TaskResultProducer taskResultProducer;
    @KafkaListener(topics = "${kafka.task.consumer.topic}", groupId = "{kafka.consumer.group.id}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, String> record) {

        log.info("consume: {}", record);

        ObjectMapper mapper = new ObjectMapper();

        log.info("Received message: ({}, {})", record.key(), record.value());
        RechargingMoneyTask task = null;

        try {
            task = mapper.readValue(record.value(), RechargingMoneyTask.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("task: {}", task);
        for (SubTask subTask : task.getSubTasks()) {
            log.info("subTask: {}", subTask);
            //TODO: subTask를 이용해서 각각의 서비스를 호출
            SubTask.SubTaskType subTaskType = subTask.getSubTaskType();

            if (subTaskType == SubTask.SubTaskType.MEMBERSHIP) {
                //TODO: 멤버십 서비스 호출 코드 작성(멤버 유효성 여부 확인)
                log.info("membershipId: {}, subTaskType: {}", subTask.getMembershipId(), subTask.getSubTaskType());

            }

            if (subTaskType == SubTask.SubTaskType.BANKING) {
                log.info("membershipId: {}, subTaskType: {}", subTask.getMembershipId(), subTask.getSubTaskType());
            }
            subTask.setSubTaskStatus(SubTask.SubTaskStatus.COMPLETED);
        }

        taskResultProducer.sendTaskResult(task.getTaskId(), task);
    }

}

