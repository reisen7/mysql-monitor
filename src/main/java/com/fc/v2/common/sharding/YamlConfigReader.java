package com.fc.v2.common.sharding;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName YamlConfigReader
 * @Author reisen
 * @Description
 * @date 2024年11月12日
 **/
@Component
@Data
public class YamlConfigReader {

    @Value("${my.sharding.create-table.url}")
    private String url;

    @Value("${my.sharding.create-table.username}")
    private String username;

    @Value("${my.sharding.create-table.password}")
    private String password;

    public void printConfig() {
        System.out.println("URL: " + url);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }


}