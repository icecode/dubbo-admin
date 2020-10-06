package org.apache.dubbo.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author libing
 * @version 1.0
 * @date 2020/10/6 11:02 上午
 */
@Configuration
@ConfigurationProperties("dubbo.registry")
public class RegistryProperties extends DubboProtocolProperties {

}
