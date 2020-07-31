package com.jordanec.store.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.jordanec.store.consumer.entity")
@EnableJpaRepositories(basePackages = "com.jordanec.store.consumer.repository")
public class ConsumerApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(ConsumerApp.class, args);
    }
}
