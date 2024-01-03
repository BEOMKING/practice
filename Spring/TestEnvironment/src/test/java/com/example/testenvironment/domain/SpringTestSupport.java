package com.example.testenvironment.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = SpringTestSupport.ContainerPropertyInitializer.class)
public class SpringTestSupport {
    @Container
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("test");

//    @Container
//    private static final DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(
//            new File("src/test/resources/docker-compose.yml"))
//            .withLocalCompose(true);

    @Autowired
    protected Environment environment;

    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of("container.port=" + postgreSQLContainer.getMappedPort(5432)).
                    applyTo(applicationContext.getEnvironment());
        }
    }
}
