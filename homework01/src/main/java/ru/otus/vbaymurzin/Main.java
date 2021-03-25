package ru.otus.vbaymurzin;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.vbaymurzin.service.TestQuestionService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestQuestionService service = context.getBean(TestQuestionService.class);
        service.getTestQuestions()
                .forEach(testQuestion -> System.out.println("Question: " + testQuestion.getQuestion() +
                        "\nAnswer options: " + testQuestion.getAnswers()));
        context.close();
    }
}
