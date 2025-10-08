package ru.yandex.practicum.delivery.parcel;

public class StandardParcel extends Parcel {
    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public int calculateDeliveryCost() {
        return ParcelType.STANDARD.getParcelAmount() * super.weight;
    }
}
