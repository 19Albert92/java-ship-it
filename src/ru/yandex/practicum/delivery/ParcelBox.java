package ru.yandex.practicum.delivery;

import ru.yandex.practicum.delivery.parcel.Parcel;

import java.util.ArrayList;

public class ParcelBox <T extends Parcel> {

    private final int maxWeight;

    private final ArrayList<T> parsels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public T addParcel(T parcel) {
        if (hasFreeSpace(parcel.getWeight())) {
            parsels.add(parcel);
        } else {
            System.out.println("Посылка превышает допустимыый вес");
        }

        return parcel;
    }

    public ArrayList<T> getAllParcels() {
        return parsels;
    }

    private boolean hasFreeSpace(int weight) {
        int boxWight = 0;

        for (T parcel : parsels) {
            boxWight += parcel.getWeight();
        }

        return (boxWight + weight) <= maxWeight;
    }
}
