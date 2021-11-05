package com.conpresp.conprespapi;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class DatabaseContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        var dockerImageName = DockerImageName.parse("mysql");

        var mysqlServerContainer = new MySQLContainer(dockerImageName).withUsername("root").withPassword("123");
        mysqlServerContainer.start();

        TestPropertyValues.of(
            "spring.datasource.url=" + mysqlServerContainer.getJdbcUrl(),
            "spring.datasource.username=" + mysqlServerContainer.getUsername(),
            "spring.datasource.password=" + mysqlServerContainer.getPassword()
        ).applyTo(applicationContext.getEnvironment());
    }
}
