package org.opennuri.study.architecture.money.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka")
@Getter @Setter
public class KafkaProperties {

    @Value("${kafka.clusters.bootstrapservers}")
    public  String BOOTSTRAP_SERVERS;

    @Value("${kafka.consumer.group.id}")
    public  String CONSUMER_GROUP_ID;

    @Value("${kafka.enable.auto.commit}")
    public  Boolean ENABLE_AUTO_COMMIT;

    @Value("${kafka.auto.commit.interval.ms}")
    public  Integer AUTO_COMMIT_INTERVAL_MS;

    @Value("${kafka.auto.offset.reset}")
    public  String AUTO_OFFSET_RESET;

    @Value("${kafka.key.deserializer}")
    public  String KEY_DESERIALIZER;

    @Value("${kafka.value.deserializer}")
    public  String VALUE_DESERIALIZER;

}
