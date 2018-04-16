package ua.nure.ponomarev.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.ponomarev.dao.CinemaDao;
import ua.nure.ponomarev.enity.Cinema;
import ua.nure.ponomarev.enity.User;
import ua.nure.ponomarev.holder.EntityManagerHolder;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@Repository
public class SqlCinemaDaoImpl implements CinemaDao {

    private EntityManagerHolder entityManagerHolder;

    @Override
    public int insertCinema(Cinema cinema) {
        EntityManager entityManager = entityManagerHolder.getEntityManager();
        entityManager.persist(cinema);
        return cinema.getId();
    }

    @Override
    public Cinema getCinema(int id) {
        return entityManagerHolder.getEntityManager()
                .find(Cinema.class, id);
    }

    @Override
    public void deleteCinema(Cinema cinema) {
        entityManagerHolder.getEntityManager()
                .remove(cinema);
    }

    @Override
    public void updateCinema(Cinema cinema) {
        entityManagerHolder.getEntityManager()
                .refresh(cinema);
    }

    @Override
    public List<Cinema> getAllCinema() {
        return new ArrayList<Cinema>(entityManagerHolder.getEntityManager().createNamedQuery("Cinema.getAll").getResultList());

    }

    @Autowired
    public void setEntityManagerHolder(EntityManagerHolder entityManagerHolder) {
        this.entityManagerHolder = entityManagerHolder;
    }
}
