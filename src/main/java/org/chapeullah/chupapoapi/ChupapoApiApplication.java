package org.chapeullah.chupapoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ChupapoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChupapoApiApplication.class, args);
    }

}
