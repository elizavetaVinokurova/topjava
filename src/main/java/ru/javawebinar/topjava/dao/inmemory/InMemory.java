package ru.javawebinar.topjava.dao.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface InMemory {

    void addMeal(Meal meal);

    void deleteMeal(int mealId);

    void updateMeal(Meal meal);

    List<Meal> getAllMeals();

    Meal getMealById(int mealId);

    List<MealTo> getAllMealsTo();
}
