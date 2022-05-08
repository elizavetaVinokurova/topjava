package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal get(int id) {
        return service.get(id, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

    // TODO add assureIdConsistent()
    public void update(Meal meal, int id) {
        service.update(meal, authUserId());
    }

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    // TODO add checkNew()
    public Meal create(Meal meal) {
        return service.create(meal, authUserId());
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getFilteredTos(service.getBetween(authUserId(), startDate, endDate), authUserCaloriesPerDay(), startTime, endTime);
    }

}