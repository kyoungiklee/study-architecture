package org.opennuri.study.architecture.money.config;


import org.opennuri.study.architecture.common.config.JpaAuditingConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        JpaAuditingConfig.class
})
public class AppConfig {
}
