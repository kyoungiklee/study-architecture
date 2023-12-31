package org.opennuri.study.architecture.money.adapter.in.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.opennuri.study.architecture.common.CountDownLatchManager;
import org.opennuri.study.architecture.common.LoggingProducer;
import org.opennuri.study.architecture.common.task.RechargingMoneyTask;
import org.opennuri.study.architecture.common.task.SubTask;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@RequiredArgsConstructor
public class RechargingMoneyResultConsumer {
    private final CountDownLatchManager countDownLatchManager;
    private final LoggingProducer loggingProducer;
    @KafkaListener(topics = "${kafka.task.consumer.topic}"
            , groupId = "${kafka.consumer.group.id}"
            , containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, String> record) {
        log.info("consume key: {} consume value: {}", record.key(), record.value());

        RechargingMoneyTask task;
        try {
            task = RechargingMoneyTask.fromJson(record.value());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<SubTask> subTasks = task.getSubTasks();
        boolean isCompleted = true;

        for (SubTask subTask : subTasks) {
            log.info("subTask: {}", subTask);
            if (subTask.getSubTaskStatus() != SubTask.SubTaskStatus.COMPLETED) {
                if(subTask.getSubTaskType() == SubTask.SubTaskType.MEMBERSHIP) {
                    loggingProducer.sendLog(task.getTaskId(), "MEMBERSHIP_CHECK_FAILED"); // 로그 전송
                    countDownLatchManager.setDataForKey(task.getTaskId(), "MEMBERSHIP_CHECK_FAILED");
                } else if(subTask.getSubTaskType() == SubTask.SubTaskType.BANKING) {
                    loggingProducer.sendLog(task.getTaskId(), "ACCOUNT_CHECK_FAILED"); // 로그 전송
                    countDownLatchManager.setDataForKey(task.getTaskId(), "ACCOUNT_CHECK_FAILED");
                }
                isCompleted = false;
                break;
            }
        }
        // 모든 작업이 완료되었을 때
        if (isCompleted) {
            loggingProducer.sendLog(task.getTaskId(), "RechargingMoneyTask is completed");
            countDownLatchManager.setDataForKey(task.getTaskId(), "SUCCESS");
        }

        CountDownLatch countDownLatch = countDownLatchManager.getCountDownLatch(task.getTaskId());
        if(countDownLatch == null) {
            log.info("countDownLatch is null");
            throw new RuntimeException("countDownLatch is null");
        }
        // await하고 있는 쓰레드를 깨운다.
        countDownLatch.countDown();
    }
}
