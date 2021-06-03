package ru.otus.vbaymurzin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.vbaymurzin.service.TestingService;

@SpringBootApplication
public class VbaymurzinApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VbaymurzinApplication.class, args);

        TestingService testingService = context.getBean(TestingService.class);
        testingService.startTesting();

    }

}
