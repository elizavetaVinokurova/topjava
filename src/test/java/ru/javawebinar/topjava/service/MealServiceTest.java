package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(PIZZA_ID, ADMIN_ID);
        assertMatch(meal, MealTestData.pizza);
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(PIZZA_ID, ADMIN_ID), getUpdated());
    }

    @Test
    public void delete() {
        service.delete(CHICKEN_MEATBALLS_ID, USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(CHICKEN_MEATBALLS_ID, USER_ID));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, carbonara, chickenMeatBalls);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(MAY_12, MAY_12, USER_ID);
        assertMatch(betweenInclusive, chickenMeatBalls);
    }

    @Test
    public void deleteNotYours() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(PIZZA_ID, USER_ID));
    }

    @Test
    public void getNotYours() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(PIZZA_ID, USER_ID));
    }

    @Test
    public void updateNotYours() {
        Assert.assertThrows(NotFoundException.class, () -> service.update(getUpdated(), USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        Assert.assertThrows(DuplicateKeyException.class, () -> service.create(
                new Meal(LocalDateTime.of(2022, Month.MAY, 12, 8, 30),
                        "laxus", 9999), USER_ID));
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }
}