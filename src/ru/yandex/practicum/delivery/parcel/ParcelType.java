package ru.yandex.practicum.delivery.parcel;

public enum ParcelType {
    STANDARD(2),
    PERISHABLE(3),
    FRAGILE(4);

    private final int parcelTypeAmount;

    ParcelType(int parcelTypeAmount) {
        this.parcelTypeAmount = parcelTypeAmount;
    }

    public int getParcelAmount() {
        return parcelTypeAmount;
    }
}
