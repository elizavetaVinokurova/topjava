package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger();

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} with userId {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }

        // чтобы не было NPE проверочку запилим...
        if (repository.get(meal.getId()) == null) {
            return null;
        }

        // а это уже проверочка по ТЗ: проверить принадлежит ли еда юзеру
        if (repository.get(meal.getId()).getUserId() != userId) {
            return null;
        }

        // устанавливаем userId: в теле запроса еда приходит на контроллер без него
        // он устанавливается нашей пока-еще-фиктивной аутентификацией
        meal.setUserId(userId);
        return repository.put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} with userId {}", id, userId);
        // чтобы не было NPE проверочку запилим...
        if (repository.get(id) == null) {
            return false;
        }

        // а это уже проверочка по ТЗ: проверить принадлежит ли еда юзеру
        if (repository.get(id).getUserId() != userId) {
            return false;
        }

        repository.remove(id);
        // По ТЗ надо вернуть булину: знаю, код выглядит дерьмово give me a break
        return true;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} with userId {}", id, userId);
        // чтобы не было NPE проверочку запилим...
        if (repository.get(id) == null) {
            return null;
        }

        // а это уже проверочка по ТЗ: проверить принадлежит ли еда юзеру
        if (repository.get(id).getUserId() != userId) {
            return null;
        }

        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll with userId {}", userId);
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getBetween(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getBetween userId {} startDate {} endDate {}", userId, startDate, endDate);
        Predicate<Meal> mealPredicate1 = startDate == null ? meal -> true : meal -> meal.getDate().compareTo(startDate) >= 0;
        Predicate<Meal> mealPredicate2 = endDate == null ? meal -> true : meal -> meal.getDate().compareTo(endDate) <= 0;

        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(mealPredicate1.and(mealPredicate2))
                .collect(Collectors.toList());
    }
}

