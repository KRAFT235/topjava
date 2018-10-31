package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId)
    {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        User user =  em.getReference(User.class,userId);
        if (meal.isNew()) {
            meal.setUser(user);
            em.persist(meal);
            return meal;
        } else {
            meal.setUser(user);
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User user = em.find(User.class,userId);
        return   em.createQuery("DELETE FROM Meal m WHERE m.id=:id and m.user.id =:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId)
    {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id and m.user.id =:userId");
        query.setParameter("id", id);
        query.setParameter("userId", em.find(User.class,userId).getId());
        List<Meal> meals = query.getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_MEALS_BY_USER, Meal.class).setParameter("userid", em.find(User.class,userId).getId()).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_MEALS_BETWEEN,Meal.class)
                .setParameter("userid",userId)
                .setParameter("date_one",startDate)
                .setParameter("date_two",endDate)
                .getResultList();
    }
}