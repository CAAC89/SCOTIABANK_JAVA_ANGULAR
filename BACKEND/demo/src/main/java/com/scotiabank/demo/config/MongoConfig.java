package com.scotiabank.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig { // enable @CreatedDate y @LastModifiedDate
}
