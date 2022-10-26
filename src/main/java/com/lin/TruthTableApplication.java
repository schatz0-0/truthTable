package com.lin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Component;

//手动加载自定义配置文件

@Component
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class TruthTableApplication {

    public static void main(String[] args) {
        SpringApplication.run(TruthTableApplication.class);
    }


}


