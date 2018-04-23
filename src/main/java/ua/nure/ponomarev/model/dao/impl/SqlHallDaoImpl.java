package ua.nure.ponomarev.model.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.nure.ponomarev.model.dao.HallDao;
import ua.nure.ponomarev.model.enity.Hall;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Bogdan_Ponamarev.
 */
public class SqlHallDaoImpl implements HallDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public int insertHall(Hall hall) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(hall);
        return hall.getId();
    }

    @Override
    public Hall getHall(int id) {
        return entityManagerFactory.createEntityManager()
                .find(Hall.class, id);
    }

    @Override
    public void deleteHall(Hall hall) {
        entityManagerFactory.createEntityManager()
                .remove(hall);
    }

    @Override
    public void updateHall(Hall hall) {
        entityManagerFactory.createEntityManager()
                .refresh(hall);
    }


}
