package ru.otus.vbaymurzin;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.vbaymurzin.service.TestingService;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TestingService service = context.getBean(TestingService.class);

        service.startTesting(System.in, System.out);
    }
}
