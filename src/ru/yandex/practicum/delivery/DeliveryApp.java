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
    private static final List<Parcel> allParcels = new ArrayList<>();
    private static final List<Trackable> trackedParcels = new ArrayList<>();
    private static final UtilAnswerMethod answerMethods = new  UtilAnswerMethod(scanner);

    private static final ParcelBox<StandardParcel> standardParcels = new ParcelBox<>(950);
    private static final ParcelBox<FragileParcel> fragileParcels = new ParcelBox<>(900);
    private static final ParcelBox<PerishableParcel> perishableParcels = new ParcelBox<>(700);

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
                case 4:
                    changeReportStatus();
                    break;
                case 5:
                    showContentOfTheBox();
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
        System.out.println("4 — Мониторинг доставки");
        System.out.println("5 — Показать содержимое коробки");
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
            case 1 -> standardParcels.addParcel(new StandardParcel(description, weight, deliveryAddress, sendDay));
            case 2 -> fragileParcels.addParcel(new FragileParcel(description, weight, deliveryAddress, sendDay));
            case 3 -> perishableParcels.addParcel(
                    new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive));
            default -> null;
        };
    }

    private static void addParcel() {
        int command = Integer.parseInt(answerMethods.createQuestion("""
                Выберите тип доставки:
                    1 — Стандартная посылка
                    2 — Хрупкая посылка
                    3 — Скоропортящаяся посылка
                """));

        Parcel parcel = createParcel(command);

        if (parcel != null) {
            allParcels.add(parcel);

            if (parcel instanceof FragileParcel fragileParcel) {
                trackedParcels.add(fragileParcel);
            }
        } else {
            System.out.println("Такого типа посылок нет");
        }
    }

    private static void changeReportStatus() {

        if (trackedParcels.isEmpty()) {
            System.out.println("\nУ вас пока нет хрупких посылок!\n");
            return;
        }

        String newDeliveryAddress = answerMethods.createQuestion("Какое новое мостоположения посылки?");

        for (Trackable parcel : trackedParcels) {
            parcel.reportStatus(newDeliveryAddress);
        }
    }

    private static void sendParcels() {

        allParcels.forEach(parcel -> {
            parcel.packageItem();
            parcel.deliver();
        });
    }

    private static void calculateCosts() {

        int allAmount = 0;

        for (Parcel parcel : allParcels) {
            allAmount += parcel.calculateDeliveryCost();
        }

        System.out.printf("Стоимость доставки составит %d\n", allAmount);
    }

    private static void showContentOfTheBox() {
        int command = Integer.parseInt(answerMethods.createQuestion("""
                Выберите тип коробки:
                    1 — Стандартная посылка
                    2 — Хрупкая посылка
                    3 — Скоропортящаяся посылка
                """));

        switch (command) {
            case 1 -> printParcelsFromTheBox(standardParcels.getAllParcels());
            case 2 -> printParcelsFromTheBox(fragileParcels.getAllParcels());
            case 3 -> printParcelsFromTheBox(perishableParcels.getAllParcels());
            default -> System.out.println("Такого типа нет");
        }
    }

    private static <T extends Parcel> void printParcelsFromTheBox(ArrayList<T> parcels) {

        if (parcels.isEmpty()) {
            System.out.println("В выбранном боксе нет посылок");
            return;
        }

        for (T parcel : parcels) {
            System.out.printf("%s \n", parcel);
        }
    }
}

