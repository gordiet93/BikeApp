package com.example.projects.data;

import com.example.projects.model.Bike;
import com.example.projects.model.Departure;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class DepartureRepository {

    @Inject
    private EntityManager em;

    public List<Departure> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Departure> criteriaQuery = cb.createQuery(Departure.class);
        Root<Departure> departure = criteriaQuery.from(Departure.class);

        criteriaQuery.select(departure).orderBy(cb.asc(departure.get("departureId")));
        return em.createQuery(criteriaQuery).getResultList();
    }

    public Departure getByBike(long bikeId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Departure> criteriaQuery = cb.createQuery(Departure.class);
        Root<Departure> departure = criteriaQuery.from(Departure.class);

        criteriaQuery.where(cb.equal(departure.get("bike"), bikeId));
        return em.createQuery(criteriaQuery).getResultList().stream().findFirst().orElse(null);
    }

    public void addDeparture(Departure departure) {
        em.persist(departure);
    }

    public void deleteDeparture(Departure departure) {
        em.remove(departure);
        em.flush();
    }
}
