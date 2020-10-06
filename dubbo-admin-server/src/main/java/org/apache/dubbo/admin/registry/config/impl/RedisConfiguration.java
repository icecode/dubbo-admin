/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.admin.registry.config.impl;

import org.apache.dubbo.admin.common.util.Constants;
import org.apache.dubbo.admin.registry.config.GovernanceConfiguration;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashSet;
import java.util.Set;

import static org.apache.dubbo.common.constants.CommonConstants.*;


@SPI("redis")
public class RedisConfiguration implements GovernanceConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    private URL url;

    private JedisPool pool;

    Set<HostAndPort> jedisClusterNodes;

    private String group;

    @Override
    public void init() {
        group = url.getParameter(Constants.GROUP_KEY, "DEFAULT_GROUP");
        if (url.getParameter(CLUSTER_KEY, false)) {
            jedisClusterNodes = new HashSet<>();
            String[] addresses = COMMA_SPLIT_PATTERN.split(url.getAddress());
            for (String address : addresses) {
                URL tmpUrl = url.setAddress(address);
                jedisClusterNodes.add(new HostAndPort(tmpUrl.getHost(), tmpUrl.getPort()));
            }
        } else {
            pool = new JedisPool(new JedisPoolConfig(), url.getHost(), url.getPort(), url.getParameter(TIMEOUT_KEY, DEFAULT_TIMEOUT), url.getPassword());
        }
    }

    @Override
    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public String setConfig(String key, String value) {
        return setConfig(group, key, value);
    }

    @Override
    public String getConfig(String key) {
        return getConfig(group, key);
    }

    @Override
    public boolean deleteConfig(String key) {
        return deleteConfig(group, key);
    }

    @Override
    public String setConfig(String group, String key, String value) {
        try (Jedis client = pool.getResource()) {
            client.set(getStoreKey(group, key), value);
            return value;
        } catch (JedisException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getStoreKey(String group, String key) {
        return String.format("%s:%s", group, key);
    }

    @Override
    public String getConfig(String group, String key) {
        try (Jedis client = pool.getResource()) {
            return client.get(getStoreKey(group, key));
        } catch (JedisException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean deleteConfig(String group, String key) {
        try (Jedis client = pool.getResource()) {
            return client.del(getStoreKey(group, key)) == 1;
        } catch (JedisException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public String getPath(String key) {
        return null;
    }

    @Override
    public String getPath(String group, String key) {
        return null;
    }

}
