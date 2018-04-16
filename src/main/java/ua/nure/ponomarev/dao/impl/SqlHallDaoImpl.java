package ua.nure.ponomarev.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.nure.ponomarev.dao.HallDao;
import ua.nure.ponomarev.enity.Film;
import ua.nure.ponomarev.enity.Hall;
import ua.nure.ponomarev.holder.EntityManagerHolder;

import javax.persistence.EntityManager;

/**
 * @author Bogdan_Ponamarev.
 */
public class SqlHallDaoImpl implements HallDao {
    private EntityManagerHolder entityManagerHolder;

    @Override
    public int insertHall(Hall hall) {
        EntityManager entityManager = entityManagerHolder.getEntityManager();
        entityManager.persist(hall);
        return hall.getId();
    }

    @Override
    public Hall getHall(int id) {
        return entityManagerHolder.getEntityManager()
                .find(Hall.class, id);
    }

    @Override
    public void deleteHall(Hall hall) {
        entityManagerHolder.getEntityManager()
                .remove(hall);
    }

    @Override
    public void updateHall(Hall hall) {
        entityManagerHolder.getEntityManager()
                .refresh(hall);
    }

    @Autowired
    public void setEntityManagerHolder(EntityManagerHolder entityManagerHolder) {
        this.entityManagerHolder = entityManagerHolder;
    }
}
