package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {


    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            Meal ref = em.getReference(Meal.class, meal.getId());
            try {
                if (ref.getUser().getId() == userId) {
                    meal.setUser(ref.getUser());
                    return em.merge(meal);
                }
            } catch (EntityNotFoundException e) {
                return null;
            }
            return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createQuery("delete from Meal m where m.id=:id and m.user.id=:user_id")
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> resultList = em.createQuery(
                        "select m from Meal m where m.id=:id and m.user.id=:user_id", Meal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getResultList();

        return DataAccessUtils.singleResult(resultList);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("select m from Meal m where m.user.id=:user_id order by m.dateTime desc", Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createQuery("select m from Meal m where m.user.id=:user_id" +
                " and m.dateTime>=:startDateTime and m.dateTime<:endDateTime order by m.dateTime desc", Meal.class)
                .setParameter("user_id", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();

    }
}