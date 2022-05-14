package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;

public class MealTestData {
    public static final int CHICKEN_MEATBALLS_ID = 100003;
    public static final int CARBONARA_ID = 100004;
    public static final int PIZZA_ID = 100005;
    public static final LocalDate MAY_12 = LocalDate.of(2022, Month.MAY, 12);

    public static final Meal pizza = new Meal(PIZZA_ID, LocalDateTime.now(), "pizza", 999);
    public static final Meal chickenMeatBalls = new Meal(CHICKEN_MEATBALLS_ID, LocalDateTime.now(), "chicken meatballs", 700);
    public static final Meal carbonara = new Meal(CARBONARA_ID, LocalDateTime.now(), "carbonara", 800);

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "Benimaru's coke'", 3333);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(pizza);
        updated.setDescription("Benimaru and Veldora");
        updated.setCalories(7777);
        updated.setDateTime(LocalDateTime.now());
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }

}
