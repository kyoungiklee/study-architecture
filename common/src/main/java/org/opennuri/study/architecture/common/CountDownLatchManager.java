package org.opennuri.study.architecture.common;

import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Configuration
public class CountDownLatchManager {

    // CountDownLatch란 쓰레드가 2개 이상일 경우 일정 개수의 쓰레드가 끝난 후
    // 다음 쓰레드가 실행될 수 있도록 대기시키고, 끝나면, 다음 쓰레드가 실행될 수 있도록 하는 것이다
    private final Map<String, CountDownLatch> countDownLatchMap;
    private final Map<String, String> stringMap;

    public CountDownLatchManager() {
        this.countDownLatchMap = new HashMap<>();
        this.stringMap = new HashMap<>();
    }

    public void addCountDownLatch(String key, int count) {
        countDownLatchMap.put(key, new CountDownLatch(count));
    }

    public void setDataForKey(String key, String data) {
        stringMap.put(key, data);
    }

    public String getDataForKey(String key) {
        return stringMap.get(key);
    }

    public CountDownLatch getCountDownLatch(String key) {
        return countDownLatchMap.get(key);
    }
}
