package com.jordanec.store.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.jordanec.store.producer.entity")
@EnableJpaRepositories(basePackages = "com.jordanec.store.producer.repository")
public class ProducerApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(ProducerApp.class, args);
    }
}
