package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.inmemory.InMemoryMealsImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.dao.inmemory.InMemoryMealsImpl.meals;

public class MealsUtil {

    public static final int CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        new InMemoryMealsImpl();
        List<MealTo> mealsTo = filteredByStreamsWithoutFiltering(meals, CALORIES_PER_DAY);
        mealsTo.forEach(System.out::println);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealTo> filteredByStreamsWithoutFiltering(List<Meal> meals, int caloriesPerDay) {
        return filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess, meal.getId());
    }
}
