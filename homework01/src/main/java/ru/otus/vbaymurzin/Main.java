package ru.otus.vbaymurzin;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.vbaymurzin.service.TestingService;

import java.util.Scanner;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TestingService service = context.getBean(TestingService.class);

        service.startTesting(new Scanner(System.in), System.out);
    }
}
