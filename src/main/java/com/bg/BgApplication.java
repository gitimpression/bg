package com.bg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ctp
 * @date 2022/11/22 3:33
 */

@SpringBootApplication
@MapperScan("com.bg.mapper")
public class BgApplication {
    public static void main(String[] args) {
        SpringApplication.run(BgApplication.class, args);
    }
}
