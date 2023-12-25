package org.opennuri.study.architecture.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class CountDownLatchManager {

    // CountDownLatch란 쓰레드가 2개 이상일 경우 일정 개수의 쓰레드가 끝난 후
    // 다음 쓰레드가 실행될 수 있도록 대기시키고, 끝나면, 다음 쓰레드가 실행될 수 있도록 하는 것이다
    private static final Map<String, CountDownLatch> countDownLatchMap = new HashMap<>();
    private static final Map<String, String> stringMap = new HashMap<>();

    public void addCountDownLatch(String key, int count) {

        log.info("addCountDownLatch: {}", key);
        countDownLatchMap.put(key, new CountDownLatch(count));
    }

    public void setDataForKey(String key, String data) {

        log.info("setDataForKey: {}", key);
        stringMap.put(key, data);
    }

    public String getDataForKey(String key) {
        log.info("getDataForKey: {}", key);
        return stringMap.get(key);
    }

    public CountDownLatch getCountDownLatch(String key) {
        log.info("getCountDownLatch: {}", key);
        return countDownLatchMap.get(key);
    }

    public boolean await(String taskId) {
        try {
            log.info("await: {}", taskId);
            return countDownLatchMap.get(taskId).await(10000, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public Optional<String> getResult(String taskId) {
        log.info("getResult: {}", taskId);
        if (countDownLatchMap.get(taskId).getCount() == 0) {

            return Optional.of(stringMap.get(taskId));
        }
        return Optional.empty();
    }
}
