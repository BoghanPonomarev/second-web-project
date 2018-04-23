package ua.nure.ponomarev.model.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.ponomarev.model.dao.UserDao;
import ua.nure.ponomarev.model.enity.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
@Repository
public class SqlUserDaoImpl implements UserDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int insertUser(User user) {
        sessionFactory.getCurrentSession().persist(user);
        return user.getId();
    }

    /**
     * Method for getting user from data base by parameters
     * @param parametersMap - map of parameters in format("field",value)
     * @return - filled user or null if user with this parameters does not exist
     * @throws CredentialException - if parameter map is empty
     */
    @Override
    public User getByParameters(Map<String, Object> parametersMap) throws CredentialException {
        if (parametersMap.size() == 0) {
            throw new CredentialException("Credential map is empty");
        }
        CriteriaQuery<User> criteriaQuery = new CriteriaQueryImpl<>(
                new CriteriaBuilderImpl((SessionFactoryImpl) sessionFactory
                        .getCurrentSession()
                        .getSessionFactory())
                , User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(getPredicatesFromMap(root, parametersMap));
        Query query = (sessionFactory.getCurrentSession()).createQuery(criteriaQuery);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Predicate getPredicatesFromMap(Root<User> root, Map<String, Object> parametersMap) {
        CriteriaBuilderImpl criteriaBuilder = new CriteriaBuilderImpl((SessionFactoryImpl) sessionFactory.getCurrentSession().getSessionFactory());
        Iterator<String> iterator = parametersMap.keySet().iterator();
        String key = iterator.next();
        Predicate predicate = criteriaBuilder.equal(
                root.get(key), parametersMap.get(key));
        while (iterator.hasNext()) {
            key = iterator.next();
            predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(
                    root.get(key), parametersMap.get(key)));
        }
        return predicate;
    }

    @Override
    public User getUser(int id) {
        return entityManagerFactory.createEntityManager()
                .find(User.class, id);
    }

    @Override
    public void deleteUser(User user) {
        entityManagerFactory.createEntityManager()
                .remove(user);
    }

    @Override
    public void updateUser(User user) {
        entityManagerFactory.createEntityManager()
                .refresh(user);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<User>(entityManagerFactory.createEntityManager()
                .createNamedQuery("User.getAll").getResultList());
    }

}
