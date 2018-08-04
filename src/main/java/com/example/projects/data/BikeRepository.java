package com.example.projects.data;

import com.example.projects.model.Bike;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by GTaggart on 01/03/2018.
 */
@ApplicationScoped
public class BikeRepository {

    @Inject
    private EntityManager em;

    public Bike findById(Long id) {
        return em.find(Bike.class, id);
    }

    public void register(Bike bike) {
        em.persist(bike);
    }

    public void merge(Bike bike) {
        em.merge(bike);
    }

    public List<Bike> findAllOrderedByStationName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bike> criteriaQuery = cb.createQuery(Bike.class);
        Root<Bike> bike = criteriaQuery.from(Bike.class);
        criteriaQuery.select(bike).orderBy(cb.asc(bike.get("currentStation")));
        return em.createQuery(criteriaQuery).getResultList();
    }

    public void setAllBikeTrackedToFalse() {
        Query q = em.createNativeQuery(
                "UPDATE bike " +
                        "SET tracked = 0 " +
                        "WHERE tracked = 1");
        q.executeUpdate();
    };
}
