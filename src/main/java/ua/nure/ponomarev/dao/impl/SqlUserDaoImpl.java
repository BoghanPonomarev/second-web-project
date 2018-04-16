package ua.nure.ponomarev.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.enity.User;
import ua.nure.ponomarev.holder.EntityManagerHolder;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@Repository
public class SqlUserDaoImpl implements UserDao {

    private EntityManagerHolder entityManagerHolder;

    @Override
    public int insertUser(User user){
        EntityManager entityManager = entityManagerHolder.getEntityManager();
        entityManager.persist(user);
        return user.getId();
    }

    @Override
    public User getUser(int id) {
        return entityManagerHolder.getEntityManager()
                .find(User.class, id);
    }

    @Override
    public void deleteUser(User user) {
        entityManagerHolder.getEntityManager()
                .remove(user);
    }

    @Override
    public void updateUser(User user) {
        entityManagerHolder.getEntityManager()
                .refresh(user);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<User>(entityManagerHolder.getEntityManager().createNamedQuery("User.getAll").getResultList());
    }

    @Autowired
    public void setEntityManagerHolder(EntityManagerHolder entityManagerHolder) {
        this.entityManagerHolder = entityManagerHolder;
    }
}
