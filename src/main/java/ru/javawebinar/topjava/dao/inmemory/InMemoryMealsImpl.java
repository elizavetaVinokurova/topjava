package ru.javawebinar.topjava.dao.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;

public class InMemoryMealsImpl implements InMemory {
    public static final List<Meal> meals = Collections.synchronizedList(new ArrayList<>());
    private static final AtomicInteger counter = new AtomicInteger();

    public InMemoryMealsImpl() {
        addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(counter.getAndIncrement());
        meals.add(meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        Meal meal = getMealById(mealId);
        meals.remove(meal);
        for (Meal m : getAllMeals().subList(meal.getId(), getAllMeals().size())) {
            m.setId(m.getId() - 1);
        }
        counter.decrementAndGet();
    }

    @Override
    public void updateMeal(Meal meal) {
        meals.set(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return meals;
    }

    @Override
    public Meal getMealById(int mealId) {
        return meals.get(mealId);
    }

    @Override
    public List<MealTo> getAllMealsTo() {
        return MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
    }
}
