package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.parcel.*;

import java.util.List;

public class DeliveryCostTest {

    private static List<StandardParcel> standardParcels;
    private static List<FragileParcel> fragileParcels;
    private static List<PerishableParcel> perishableParcels;

    @BeforeAll
    static void beforeAll() {
        standardParcels = List.of(
                new StandardParcel("Sample description 1", 400, "Moskow", 1),
                new StandardParcel("Sample description 3", 600, "Sochi", 10),
                new StandardParcel("Sample description 3", 600, "Sochi", 10)
        );

        fragileParcels = List.of(
                new FragileParcel("Sample description 1", 400, "Moskow", 1),
                new FragileParcel("Sample description 2", 200, "Piterburg", 5),
                new FragileParcel("Sample description 3", 600, "Sochi", 10)
        );

        perishableParcels = List.of(
                new PerishableParcel("Sample description 1", 400, "Moskow", 1,
                        3),
                new PerishableParcel("Sample description 2", 200, "Piterburg",
                        5, 7),
                new PerishableParcel("Sample description 3", 600, "Sochi", 10,
                        10)
        );
    }

    @Test
    public void calculateParcelCostForEachStandard() {
        for (Parcel parcel : standardParcels) {
            Assertions.assertEquals(parcel.getWeight() * ParcelType.STANDARD.getParcelAmount(),
                    parcel.calculateDeliveryCost(),
                    "Стоимость стандартной посылки выщитывается не правильно"
            );
        }
    }

    @Test
    public void calculateParcelCostForEachFragile() {

        for (Parcel parcel : fragileParcels) {
            Assertions.assertEquals(parcel.getWeight() * ParcelType.FRAGILE.getParcelAmount(),
                    parcel.calculateDeliveryCost(),
                    "Стоимость хрупкой посылки выщитывается не правильно"
            );
        }
    }

    @Test
    public void calculateParcelCostForEachPerishable() {

        for (Parcel parcel : perishableParcels) {
            Assertions.assertEquals(parcel.getWeight() * ParcelType.PERISHABLE.getParcelAmount(),
                    parcel.calculateDeliveryCost(),
                    "Стоимость скоропортящийся посылки выщитывается не правильно"
            );
        }
    }

    @Test
    public void shouldReturnFalseBecausePerishableParcelIsNotExpired() {
        Assertions.assertFalse(perishableParcels.get(1).isExpired(7),
                "Посылка не просроченна еще, должно было вернуться true");
        Assertions.assertFalse(perishableParcels.get(2).isExpired(20),
                "Посылка не просроченна еще, должно было вернуться true");
    }

    @Test
    public void shouldReturnTrueBecausePerishableParcelIsExpired() {
        Assertions.assertTrue(perishableParcels.get(1).isExpired(13),
                "Посылка просроченна на несколько 9 дней, должно было вернуться false");
        Assertions.assertTrue(perishableParcels.get(2).isExpired(21),
                "Посылка просроченна на несколько 1 день, должно было вернуться false");
    }

    @Test
    public void shouldAddedParcelIfTheMaximumWeightOfTheBoxIsNotExceeded() {
        ParcelBox<StandardParcel> parcelBox = new ParcelBox<>(1000);

        for (int i = 0; i < standardParcels.size(); i++) {

            parcelBox.addParcel(standardParcels.get(i));

            if (i == 2) {
                Assertions.assertEquals(2, parcelBox.getAllParcels().size(),
                        "Последняя посылка не должна была добавиться");
            } else {
                Assertions.assertEquals(i + 1, parcelBox.getAllParcels().size());
            }
        }
    }
}
