package eu.udrzal.rental;

import java.time.Clock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RentalApp {

    public static void main(String[] args) {
        SpringApplication.run(RentalApp.class, args);
    }

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }

}