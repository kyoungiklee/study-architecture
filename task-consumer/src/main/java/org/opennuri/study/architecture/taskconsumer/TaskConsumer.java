package org.opennuri.study.architecture.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.opennuri.study.architecture.common.task.RechargingMoneyTask;
import org.opennuri.study.architecture.common.task.SubTask;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskConsumer {

    @KafkaListener(topics = "${kafka.task.topic}", groupId = "{kafka.consumer.group.id}")
    public void consume(ConsumerRecords<String, String> records) {
        if(records != null) {
            ObjectMapper mapper = new ObjectMapper();
            for (ConsumerRecord<String, String> record : records) {
                log.info("Received message: ({}, {})", record.key(), record.value());
                RechargingMoneyTask task = null;

                try {
                    task = mapper.readValue(record.value(), RechargingMoneyTask.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                log.info("task: {}", task);
                for(SubTask subTask : task.getSubTasks()) {
                    log.info("subTask: {}", subTask);
                    //TODO: subTask를 이용해서 각각의 서비스를 호출
                    SubTask.SubTaskType subTaskType = subTask.getSubTaskType();

                    if(subTaskType == SubTask.SubTaskType.MEMBERSHIP) {
                        //TODO: 멤버십 서비스 호출 코드 작성(멤버 유효성 여부 확인)
                        log.info("membershipId: {}, subTaskType: {}", subTask.getMembershipId(), subTask.getSubTaskType());

                    }

                    if(subTaskType == SubTask.SubTaskType.BANKING) {
                        log.info("membershipId: {}, subTaskType: {}", subTask.getMembershipId(), subTask.getSubTaskType());
                    }
                    subTask.setSubTaskStatus(SubTask.SubTaskStatus.COMPLETED);
                }
            }
        }
    }
}
