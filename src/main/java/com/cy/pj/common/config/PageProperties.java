package com.cy.pj.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @configuration 描述的文象是一个配置对象, 可以由spring对其进行管理
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "db.page")
public class PageProperties {
	private int pageSize;
}
