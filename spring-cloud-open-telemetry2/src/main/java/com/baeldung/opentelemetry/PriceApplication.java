package com.baeldung.opentelemetry;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PriceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PriceApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
