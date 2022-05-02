package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.inmemory.InMemory;
import ru.javawebinar.topjava.dao.inmemory.InMemoryImpl;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String DELETE = "delete";
    private static final String EDIT = "edit";
    private static final String INSERT = "insert";
    private static final String MEALS = "/meals.jsp";
    private static final String EDIT_MEAL = "/editMeal.jsp";
    private InMemory inMemory;

    public MealServlet() {
        super();
        inMemory = new InMemoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet MealServlet");
        String forward = "";
        String action = request.getParameter("action");

        if (DELETE.equalsIgnoreCase(action)) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            inMemory.deleteMeal(mealId);
            forward = MEALS;
            request.setAttribute("mealsTo", inMemory.getAllMealsTo());
        } else if (EDIT.equalsIgnoreCase(action)) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            forward = EDIT_MEAL;
            Meal meal = inMemory.getMealById(mealId);
            request.setAttribute("meal", meal);
        } else if (INSERT.equalsIgnoreCase(action)) {
            forward = EDIT_MEAL;
        } else {
            forward = MEALS;
            request.setAttribute("mealsTo", inMemory.getAllMealsTo());
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost MealServlet");

        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        int calories = Integer.parseInt(request.getParameter("calories"));
        String mealId = request.getParameter("mealId");

        if (mealId == null || mealId.isEmpty()) {
            Meal meal = new Meal(dateTime, description, calories);
            inMemory.addMeal(meal);
        } else {
            Meal meal = new Meal();
            meal.setCalories(calories);
            meal.setDateTime(dateTime);
            meal.setDescription(description);
            meal.setId(Integer.parseInt(mealId));
            inMemory.updateMeal(meal);
        }

        request.setAttribute("mealsTo", inMemory.getAllMealsTo());
        request.getRequestDispatcher(MEALS).forward(request, response);
    }
}
