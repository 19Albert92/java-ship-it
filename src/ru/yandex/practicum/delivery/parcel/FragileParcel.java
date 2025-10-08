package ru.yandex.practicum.delivery.parcel;

public class FragileParcel extends Parcel {
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
}
