package ru.yandex.practicum.delivery;

import java.util.Scanner;

public class UtilAnswerMethod {

    private final Scanner scanner;

    public UtilAnswerMethod(Scanner scanner) {
        this.scanner = scanner;
    }

    public String createQuestion(String questionText, String errorText) {

        while (true) {

            System.out.println(questionText);

            String answer = scanner.nextLine();

            if (answer.isBlank()) {
                System.out.println(errorText);
            } else {
                return answer;
            }
        }
    }

    public String createQuestion(String questionText) {
        return this.createQuestion(questionText, "Не должно быть пустых значений");
    }
}
