package org.opennuri.study.architecture.remittance.config;

import org.opennuri.study.architecture.common.config.JpaAuditingConfig;
import org.opennuri.study.architecture.common.config.RestTemplateConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
        "org.opennuri.study.architecture.common"
})
@Import({
        JpaAuditingConfig.class,
        RestTemplateConfig.class,
        FilterConfig.class
})
public class AppConfig {
}
