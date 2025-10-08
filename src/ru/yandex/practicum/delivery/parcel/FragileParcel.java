package ru.yandex.practicum.delivery.parcel;

import ru.yandex.practicum.delivery.Trackable;

public class FragileParcel extends Parcel implements Trackable {
    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.printf("Посылка <<%s>> обёрнута в защитную плёнку\n", super.description);
        super.packageItem();
    }

    @Override
    public int calculateDeliveryCost() {
        return ParcelType.FRAGILE.getParcelAmount() * super.weight;
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.printf("Хрупкая посылка <<%s>> изменила местоположение на %s", super.description, newLocation);
    }
}
