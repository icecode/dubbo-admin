package org.apache.dubbo.admin.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.admin.common.util.UrlUtils;
import org.apache.dubbo.admin.registry.config.GovernanceConfiguration;
import org.apache.dubbo.admin.registry.config.impl.NoOpConfiguration;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.config.configcenter.ConfigurationListener;
import org.apache.dubbo.common.config.configcenter.DynamicConfiguration;
import org.apache.dubbo.common.config.configcenter.DynamicConfigurationFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.SortedSet;

/**
 * @author libing
 * @version 1.0
 * @date 2020/10/6 11:09 上午
 */
@Configuration
public class ConfigCenterBeanConfig {

    @Bean
    public DynamicConfiguration getConfigCenter(ConfigCenterProperties config) {
        DynamicConfiguration dynamicConfiguration;
        if (StringUtils.isNotEmpty(config.getAddress())) {
            URL configCenterUrl = UrlUtils.create(config);
            DynamicConfigurationFactory dynamicConfigurationFactory = ExtensionLoader.getExtensionLoader(DynamicConfigurationFactory.class).getExtension(configCenterUrl.getProtocol());
            dynamicConfiguration = dynamicConfigurationFactory.getDynamicConfiguration(configCenterUrl);
        } else {
            dynamicConfiguration = new NoOpDynamicConfiguration();
        }
        return dynamicConfiguration;
    }

    public static class NoOpDynamicConfiguration implements DynamicConfiguration {

        @Override
        public String getString(String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getString(String key, String defaultValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getInt(String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getInt(String key, int defaultValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer getInteger(String key, Integer defaultValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean getBoolean(String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean getBoolean(String key, boolean defaultValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean getBoolean(String key, Boolean defaultValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getProperty(String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getProperty(String key, Object defaultValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T convert(Class<T> cls, String key, T defaultValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getInternalProperty(String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addListener(String key, ConfigurationListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeListener(String key, ConfigurationListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getConfig(String key, String group) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getProperties(String key, String group) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getProperties(String key, String group, long timeout) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean publishConfig(String key, String content) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean publishConfig(String key, String group, String content) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        @Override
        public SortedSet<String> getConfigKeys(String group) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getDefaultGroup() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getDefaultTimeout() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() throws Exception {
            //ignore
        }

        @Override
        public void addListener(String key, String group, ConfigurationListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeListener(String key, String group, ConfigurationListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getConfig(String key, String group, long timeout) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }
    }
}
