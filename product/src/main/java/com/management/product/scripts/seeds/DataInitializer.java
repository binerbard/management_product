package com.management.product.scripts.seeds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DataInitializer {

    private final StatusSeeder statusSeeder;
    private final AdminSeeder adminSeeder;

    @Autowired
    public DataInitializer(StatusSeeder statusSeeder, AdminSeeder adminSeeder) {
        this.statusSeeder = statusSeeder;
        this.adminSeeder = adminSeeder;
    }

    @Bean
    public ApplicationRunner dataLoader() {
        return args -> {
            log.info("Execution Seed");
            statusSeeder.seed();
            adminSeeder.seed();
            log.info("Exit Execution");
        };
    }
}
