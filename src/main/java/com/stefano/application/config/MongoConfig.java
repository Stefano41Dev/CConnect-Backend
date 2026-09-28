package com.stefano.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@Configuration
public class MongoConfig {
    //Habilitar transactional Mongodb
    @Bean
    public MongoTransactionManager transactionManager(
            MongoDatabaseFactory mongoDatabaseFactory) {

        return new MongoTransactionManager(mongoDatabaseFactory);
    }
}
