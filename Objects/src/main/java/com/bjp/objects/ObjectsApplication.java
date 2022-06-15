package com.bjp.objects;

import com.bjp.objects.ticket_sell.Team;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(value = Team.class)
public class ObjectsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObjectsApplication.class, args);
    }

}
