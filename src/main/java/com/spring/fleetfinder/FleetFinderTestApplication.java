package com.spring.fleetfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController

//Запуск приложения, стартовая конфигурация и прочая ересь будет жить тут. Смотрим документацию Spring
public class FleetFinderTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(FleetFinderTestApplication.class, args);
    }

}