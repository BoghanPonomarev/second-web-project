package ua.nure.ponomarev.holder.impl;

import org.springframework.stereotype.Component;
import ua.nure.ponomarev.holder.EntityManagerHolder;

import javax.persistence.EntityManager;

/**
 * @author Bogdan_Ponamarev.
 */
@Component
public class EntityManagerHolderImpl implements EntityManagerHolder {
    private ThreadLocal<EntityManager> entityManagerThreadLocal=new ThreadLocal<>();


    public void putEntityManager(EntityManager entityManager) {
        entityManagerThreadLocal.set(entityManager);
    }


    public EntityManager getEntityManager() {
        return entityManagerThreadLocal.get();
    }
}
