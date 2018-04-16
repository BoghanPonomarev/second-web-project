package ua.nure.ponomarev.holder;

import javax.persistence.EntityManager;

/**
 * @author Bogdan_Ponamarev.
 */
public interface EntityManagerHolder {

    void putEntityManager(EntityManager entityManager);

    EntityManager getEntityManager();
}
