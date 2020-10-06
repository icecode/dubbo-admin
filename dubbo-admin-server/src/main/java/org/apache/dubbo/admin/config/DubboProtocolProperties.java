package org.apache.dubbo.admin.config;

import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;

/**
 * @author libing
 * @version 1.0
 * @date 2020/10/4 2:25 下午
 */
public class DubboProtocolProperties {

    protected String address;

    protected String group;

    protected String username;

    protected String password;

    public String getGroup() {
        return group == null || group.isEmpty() ? DubboProtocol.NAME : group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
