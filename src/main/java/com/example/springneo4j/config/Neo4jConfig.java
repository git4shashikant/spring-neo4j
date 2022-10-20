package com.example.springneo4j.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Logging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Configuration
public class Neo4jConfig {

    @Value("${spring.neo4j.uri}")
    private String neo4jUri;

    @Value("${spring.neo4j.authentication.username}")
    private String neo4jUsername;

    @Value("${spring.neo4j.authentication.password}")
    private String neo4jPassword;

    @Bean
    public Driver getDriver() {
        return GraphDatabase.driver(neo4jUri,
                AuthTokens.basic(neo4jUsername, neo4jPassword),
                getConfig());
    }

    private Config getConfig() {
        return Config.builder()
                .withConnectionTimeout(30, TimeUnit.SECONDS)
                .withMaxConnectionLifetime(30, TimeUnit.MINUTES)
                .withMaxConnectionPoolSize(10)
                .withConnectionAcquisitionTimeout(20, TimeUnit.SECONDS)
                .withFetchSize(1000)
                .withDriverMetrics()
                .withLogging(Logging.console(Level.INFO))
                .build();
    }
}
