package ru.javawebinar.topjava.dao.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.meals;

public class InMemoryImpl implements InMemory {

    @Override
    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        Meal meal = getMealById(mealId);
        meals.remove(meal);
        for (Meal m : getAllMeals().subList(meal.getId(), getAllMeals().size())) {
            m.setId(m.getId() - 1);
        }
        Meal.getCounter().decrementAndGet();
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
