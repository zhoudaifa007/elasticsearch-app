package org.spring.springboot.redis.model;

/**
 * Created by zhoudf2 on 2017-9-21.
 */
public class LoginParams {

    private String account;

    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
