package ru.yandex.practicum.delivery.parcel;

public class PerishableParcel extends Parcel {

    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    public boolean isExpired(int currentDay) {
        return !(super.sendDay >= currentDay) || (timeToLive >= currentDay);
    }

    @Override
    public int calculateDeliveryCost() {
        return ParcelType.PERISHABLE.getParcelAmount() * super.weight;
    }

    @Override
    public String toString() {
        return String.format("PerishableParcel{%s, timeToLive='%d'}", super.toString(), timeToLive);
    }
}
