package ru.job4j.cars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 13.03.2023
 */
@SpringBootApplication
public class CarsApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarsApplication.class.getSimpleName());
    private static final String START_PAGE = "http:/localhost:8080/index";

    public static void main(String[] args) {
        SpringApplication.run(CarsApplication.class, args);
        LOGGER.info("Go to: {}", START_PAGE);
    }
}
