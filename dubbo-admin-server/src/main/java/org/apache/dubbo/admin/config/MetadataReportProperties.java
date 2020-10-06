package org.apache.dubbo.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author libing
 * @version 1.0
 * @date 2020/10/4 2:14 下午
 */
@Configuration
@ConfigurationProperties("dubbo.metadata-report")
public class MetadataReportProperties extends DubboProtocolProperties {

}
