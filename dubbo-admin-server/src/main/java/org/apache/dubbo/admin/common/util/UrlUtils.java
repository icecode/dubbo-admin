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
package org.apache.dubbo.admin.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.admin.config.DubboProtocolProperties;
import org.apache.dubbo.common.URL;

import java.util.Map;
import java.util.Map.Entry;

/**
 * UrlUtils.java
 *
 */
public class UrlUtils {

    public static String paramsMapToString(Map<String, String[]> params) {
        StringBuilder paramsString = new StringBuilder();
        for (Entry<String, String[]> param : params.entrySet()) {
            if (paramsString != null) {
                paramsString.append("&");
            }
            paramsString.append(param.getKey());
            paramsString.append("=");
            for (int i = 0; i < param.getValue().length; i++) {
                if (i == 0) {
                    paramsString.append(param.getValue()[i]);
                } else {
                    paramsString.append(",");
                    paramsString.append(param.getValue()[i]);
                }
            }
        }
        return paramsString.toString();
    }

    public static String arrayToString(String[] values) {
        StringBuilder paramsString = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                paramsString.append(values[i]);
            } else {
                paramsString.append(",");
                paramsString.append(values[i]);
            }
        }
        return paramsString.toString();
    }

    public static URL create(DubboProtocolProperties protocolConfig) {
        return create(protocolConfig.getAddress(), protocolConfig.getGroup(), protocolConfig.getUsername(), protocolConfig.getPassword());
    }

    public static URL create(String config, String group, String username, String password) {
        URL url = URL.valueOf(config);
        if (StringUtils.isNotEmpty(group)) {
            url = url.addParameter(Constants.GROUP_KEY, group);
        }
        if (StringUtils.isNotEmpty(username)) {
            url = url.setUsername(username);
        }
        if (StringUtils.isNotEmpty(password)) {
            url = url.setPassword(password);
        }
        return url;
    }

}
