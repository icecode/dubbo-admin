package org.apache.dubbo.admin.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.admin.common.util.UrlUtils;
import org.apache.dubbo.admin.registry.metadata.MetaDataCollector;
import org.apache.dubbo.admin.registry.metadata.impl.NoOpMetadataCollector;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author libing
 * @version 1.0
 * @date 2020/10/4 2:17 下午
 */
@Component
public class MetadataBeanConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MetadataBeanConfig.class);

    @Bean
    MetaDataCollector getMetadataCollector(MetadataReportProperties properties) {
        MetaDataCollector metaDataCollector;
        if (StringUtils.isNotEmpty(properties.getAddress())) {
            URL metadataUrl = UrlUtils.create(properties);
            metaDataCollector = ExtensionLoader.getExtensionLoader(MetaDataCollector.class).getExtension(metadataUrl.getProtocol());
            metaDataCollector.setUrl(metadataUrl);
            metaDataCollector.init();
            LOG.info("CreateMetadataReportCollector conf:" + properties.getAddress());
        } else {
            metaDataCollector = new NoOpMetadataCollector();
        }
        return metaDataCollector;
    }
}
