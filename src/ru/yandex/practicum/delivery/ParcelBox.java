package ru.yandex.practicum.delivery;

import ru.yandex.practicum.delivery.parcel.Parcel;

import java.util.ArrayList;

public class ParcelBox <T extends Parcel> {

    private final int maxWeight;

    private final ArrayList<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public T addParcel(T parcel) {
        if (hasFreeSpace(parcel.getWeight())) {
            parcels.add(parcel);
        } else {
            System.out.println("Посылка превышает допустимый вес");
        }

        return parcel;
    }

    public ArrayList<T> getAllParcels() {
        return parcels;
    }

    private boolean hasFreeSpace(int weight) {
        int boxWight = 0;

        for (T parcel : parcels) {
            boxWight += parcel.getWeight();
        }

        return (boxWight + weight) <= maxWeight;
    }

    public void printParcelsFromTheBox() {
        if (parcels.isEmpty()) {
            System.out.println("В выбранном боксе нет посылок");
            return;
        }

        for (T parcel : parcels) {
            System.out.printf("%s \n", parcel);
        }
    }
}
