package ua.nure.ponomarev.model.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.ponomarev.model.dao.CinemaDao;
import ua.nure.ponomarev.model.enity.Cinema;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@Repository
public class SqlCinemaDaoImpl implements CinemaDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public int insertCinema(Cinema cinema) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(cinema);
        return cinema.getId();
    }

    @Override
    public Cinema getCinema(int id) {
        return entityManagerFactory.createEntityManager()
                .find(Cinema.class, id);
    }

    @Override
    public void deleteCinema(Cinema cinema) {
        entityManagerFactory.createEntityManager()
                .remove(cinema);
    }

    @Override
    public void updateCinema(Cinema cinema) {
        entityManagerFactory.createEntityManager()
                .refresh(cinema);
    }

    @Override
    public List<Cinema> getAllCinema() {
        return new ArrayList<Cinema>(entityManagerFactory.createEntityManager()
                .createNamedQuery("Cinema.getAll").getResultList());

    }

}
