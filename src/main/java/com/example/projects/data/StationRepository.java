package com.example.projects.data;

import com.example.projects.model.Journey;
import com.example.projects.model.Station;

import javax.ejb.Schedule;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by GTaggart on 16/03/2018.
 */
@ApplicationScoped
public class StationRepository {

    @Inject
    private EntityManager em;

    public void register(Station station) {
        em.persist(station);
    }

    public Station findById(Long id) {
        return em.find(Station.class, id);
    }

    public Station findByIdRef(Long id) {
        return em.getReference(Station.class, id);
    }

    public List<Station> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Station> criteriaQuery = cb.createQuery(Station.class);
        Root<Station> station = criteriaQuery.from(Station.class);
        criteriaQuery.select(station).orderBy(cb.asc(station.get("stationName")));
        return em.createQuery(criteriaQuery).getResultList();
    }
}
