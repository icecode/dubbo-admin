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

package org.apache.dubbo.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.admin.annotation.Authority;
import org.apache.dubbo.admin.common.exception.ParamValidationException;
import org.apache.dubbo.admin.common.exception.ResourceNotFoundException;
import org.apache.dubbo.admin.common.util.Constants;
import org.apache.dubbo.admin.model.dto.ConfigDTO;
import org.apache.dubbo.admin.service.ProviderService;
import org.apache.dubbo.common.config.configcenter.DynamicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


@Authority(needLogin = true)
@RestController
@RequestMapping("/api/{env}/manage")
public class ManagementController {

    private final DynamicConfiguration dynamicConfiguration;

    private final ProviderService providerService;

    private final static Pattern CLASS_NAME_PATTERN = Pattern.compile("([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*");

    @Autowired
    public ManagementController(DynamicConfiguration dynamicConfiguration, ProviderService providerService) {
        this.dynamicConfiguration = dynamicConfiguration;
        this.providerService = providerService;
    }

    @RequestMapping(value = "/config", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean createConfig(@RequestBody ConfigDTO config, @PathVariable String env) {
        return dynamicConfiguration.publishConfig(Constants.DUBBO_PROPERTY, config.getKey(), config.getConfig());
    }

    @RequestMapping(value = "/config/{key}", method = RequestMethod.PUT)
    public boolean updateConfig(@PathVariable String key, @RequestBody ConfigDTO configDTO, @PathVariable String env) {
        if (key == null) {
            throw new ParamValidationException("Unknown ID!");
        }
        String oldConfig = dynamicConfiguration.getConfig(Constants.DUBBO_PROPERTY, key);
        if (oldConfig == null) {
            throw new ResourceNotFoundException("Unknown ID!");
        }
        return dynamicConfiguration.publishConfig(Constants.DUBBO_PROPERTY, key, configDTO.getConfig());
    }

    @RequestMapping(value = "/config/{key}", method = RequestMethod.GET)
    public List<ConfigDTO> getConfig(@PathVariable String key, @PathVariable String env) {
        Set<String> queryApps = new HashSet<>();
        if (key.equals(Constants.ANY_VALUE)) {
            queryApps = providerService.findApplications();
            queryApps.add(Constants.GLOBAL_CONFIG_GROUP);
        } else {
            queryApps.add(key);
        }
        List<ConfigDTO> ret = new ArrayList<>();
        for (String app : queryApps) {
            String configContent = dynamicConfiguration.getConfig(Constants.DUBBO_PROPERTY, app);
            if (StringUtils.isEmpty(configContent)) {
                continue;
            }
            ConfigDTO configDTO = new ConfigDTO();
            configDTO.setKey(app);
            configDTO.setConfig(configContent);
            //configDTO.setPath(managementService.getConfigPath(q));
            if (Constants.DUBBO_PROPERTY.equals(app)) {
                configDTO.setScope(Constants.GLOBAL_CONFIG);
            } else if (CLASS_NAME_PATTERN.matcher(app).matches()) {
                configDTO.setScope(Constants.SERVICE);
            } else {
                configDTO.setScope(Constants.APPLICATION);
            }
            ret.add(configDTO);
        }
        return ret;
    }

    @RequestMapping(value = "/config/{key}", method = RequestMethod.DELETE)
    public boolean deleteConfig(@PathVariable String key, @PathVariable String env) {
        return dynamicConfiguration.removeConfig(Constants.DUBBO_PROPERTY, key);
    }
}
