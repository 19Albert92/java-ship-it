package ru.yandex.practicum.delivery;

import ru.yandex.practicum.delivery.parcel.FragileParcel;
import ru.yandex.practicum.delivery.parcel.Parcel;
import ru.yandex.practicum.delivery.parcel.PerishableParcel;
import ru.yandex.practicum.delivery.parcel.StandardParcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static final UtilAnswerMethod answerMethods = new  UtilAnswerMethod(scanner);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("0 — Завершить");
    }

    private static Parcel createParcel(int parcelType) {

        String description = answerMethods.createQuestion("Какое описание будет у пасылки?");

        int weight = Integer.parseInt(answerMethods.createQuestion("Какой вес пасылки?"));

        String deliveryAddress = answerMethods.createQuestion("Какой адрес доставки?");

        int sendDay = Integer.parseInt(answerMethods.createQuestion("Выберите день отправки?"));

        int timeToLive = 0;

        if (parcelType == 3) {
            timeToLive = Integer.parseInt(answerMethods.createQuestion("Какой срок хранения?"));
        }

        return switch (parcelType) {
            case 1 -> new StandardParcel(description, weight, deliveryAddress, sendDay);
            case 2 -> new FragileParcel(description, weight, deliveryAddress, sendDay);
            case 3 -> new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
            default -> null;
        };
    }

    private static void addParcel() {
        int command = Integer.parseInt(answerMethods.createQuestion("""
                Какой тип посылки вы бы хотели отправить?
                1 — Стандартная посылка
                2 — Хрупкая посылка
                3 — Скоропортящаяся посылка
                """));

        Parcel parcel = createParcel(command);

        if (parcel != null) {
            allParcels.add(parcel);
        }
    }

    private static void sendParcels() {

        if (allParcels.isEmpty()) {
            System.out.println("У вас пока нет посылок!");
            return;
        }

        allParcels.forEach(parcel -> {
            parcel.packageItem();
            parcel.deliver();
        });
    }

    private static void calculateCosts() {

        int allAmount = 0;

        if (allParcels.isEmpty()) {
            System.out.println("У вас пока нет посылок!");
            return;
        }

        for (Parcel parcel : allParcels) {
            allAmount += parcel.calculateDeliveryCost();
        }

        System.out.printf("Стоимость доставки составит %d\n", allAmount);
    }
}

