package org.opennuri.study.architecture.membership.config;


import org.opennuri.study.architecture.common.config.JpaAuditingConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
        "org.opennuri.study.architecture.common"
})
@Import({
        JpaAuditingConfig.class,
        FilterConfig.class
})
public class AppConfig {
}
