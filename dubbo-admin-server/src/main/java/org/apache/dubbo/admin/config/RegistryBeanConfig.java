package org.apache.dubbo.admin.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.admin.common.util.UrlUtils;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author libing
 * @version 1.0
 * @date 2020/10/6 11:01 上午
 */
@Configuration
public class RegistryBeanConfig {

    @Bean
    public Registry getRegistry(RegistryProperties properties) {
        Registry registry = null;
//        if (!(dynamicConfig instanceof NoOpConfiguration)) {
//            String globalConfig = dynamicConfig.getConfig(, DynamicConfiguration.DEFAULT_GROUP);
//            if (StringUtils.isNotEmpty(globalConfig)) {
//                String registryConfigStr = Arrays.stream(globalConfig.split("\n"))
//                        .filter(s -> s.startsWith("dubbo.registry"))
//                        .collect(Collectors.joining("\n"));
//                if (StringUtils.isNotEmpty(registryConfigStr)) {
//                    //read config-center config
//                    throw new UnsupportedOperationException();
//                }
//            }
//        } else
        if (StringUtils.isNotEmpty(properties.getAddress())) {
            RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
            registry = registryFactory.getRegistry(UrlUtils.create(properties));
        }
        return registry;
    }
}
