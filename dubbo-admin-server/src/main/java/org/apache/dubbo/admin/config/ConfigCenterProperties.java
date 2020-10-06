package org.apache.dubbo.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author libing
 * @version 1.0
 * @date 2020/10/6 11:09 上午
 */
@Configuration
@ConfigurationProperties("dubbo.config-center")
public class ConfigCenterProperties extends DubboProtocolProperties {

}
