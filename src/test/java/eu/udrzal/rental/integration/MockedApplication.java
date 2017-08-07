package eu.udrzal.rental.integration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import eu.udrzal.rental.RentalApp;

@SpringBootApplication
public class MockedApplication extends RentalApp {
    public static void main(String[] args) {
        SpringApplication.run(MockedApplication.class, args);
    }

    @Bean
    @Override
    public Clock clock() {
        return Clock.fixed(LocalDateTime.parse("2017-01-01T12:00:00.913").toInstant(ZoneOffset.UTC),
                ZoneId.systemDefault());
    }

}