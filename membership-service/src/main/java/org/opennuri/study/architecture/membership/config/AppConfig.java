package org.opennuri.study.architecture.membership.config;


import org.opennuri.study.architecture.common.config.JpaAuditingConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackages = {
        "org.opennuri.study.architecture.common"
})
@Import({
        JpaAuditingConfig.class
})
public class AppConfig {
}
