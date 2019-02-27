package com.example.projects.data;

import com.example.projects.model.Bike;
import com.example.projects.model.Journey;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by GTaggart on 05/03/2018.
 */
@ApplicationScoped
public class JourneyRepository {

    @Inject
    private EntityManager em;

    public Journey findById(long id) {
        return em.find(Journey.class, id);
    }

    public void recordJourney(Journey journey) {
        em.persist(journey);
    }

    public List<Journey> findAllOrderedById() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Journey> criteriaQuery = cb.createQuery(Journey.class);
        Root<Journey> journeyRoot = criteriaQuery.from(Journey.class);
        criteriaQuery.select(journeyRoot).orderBy(cb.desc(journeyRoot.get("journeyId")));
        return em.createQuery(criteriaQuery).getResultList();
    }

    public List<Journey> findByRoute(long startStation, long endStation) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Journey> criteriaQuery = cb.createQuery(Journey.class);
        Root<Journey> journeyRoot = criteriaQuery.from(Journey.class);

        criteriaQuery.where(
                cb.equal(journeyRoot.get("startStation"), startStation),
                cb.equal(journeyRoot.get("endStation"), endStation));

        return em.createQuery(criteriaQuery).getResultList();
    }

    public List<Journey> findByDuration(int duration, boolean greaterThan) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Journey> criteriaQuery = cb.createQuery(Journey.class);
        Root<Journey> journeyRoot = criteriaQuery.from(Journey.class);

        if (greaterThan) {
            criteriaQuery.where(
                    cb.greaterThanOrEqualTo(journeyRoot.get("duration"), duration)
            );
        } else {
            criteriaQuery.where(
                    cb.lessThanOrEqualTo(journeyRoot.get("duration"), duration)
            );
        }

        return em.createQuery(criteriaQuery).getResultList();
    }

    public List<Journey> findBetweenDuration(int low, int high) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Journey> criteriaQuery = cb.createQuery(Journey.class);
        Root<Journey> journeyRoot = criteriaQuery.from(Journey.class);

        //cb.between(journeyRoot.get("duration"), low, high); doesn't work

        criteriaQuery.where(
                cb.greaterThanOrEqualTo(journeyRoot.get("duration"), low),
                cb.lessThanOrEqualTo(journeyRoot.get("duration"), high)
        );

        return em.createQuery(criteriaQuery).getResultList();
    }

    public List<Journey> findBetweenDate(long start, long end) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Journey> criteriaQuery = cb.createQuery(Journey.class);
        Root<Journey> journeyRoot = criteriaQuery.from(Journey.class);

        criteriaQuery.where(
                cb.between(journeyRoot.get("dateTimeFinish"), new Date(start), new Date(end))
        );

        return  em.createQuery(criteriaQuery).getResultList();
    }

    public List<Journey> findByBike(long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Journey> criteriaQuery = cb.createQuery(Journey.class);
        Root<Journey> journeyRoot = criteriaQuery.from(Journey.class);

        criteriaQuery.where(cb.equal(journeyRoot.get("bike"), id));

        return em.createQuery(criteriaQuery).getResultList();
    }

    public void deleteJourney(long id) {
        Journey journey = findById(id);
        em.remove(journey);
        em.flush();
    }
}
