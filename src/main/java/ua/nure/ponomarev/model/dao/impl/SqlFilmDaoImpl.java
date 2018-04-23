package ua.nure.ponomarev.model.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.ponomarev.model.dao.FilmDao;
import ua.nure.ponomarev.model.enity.Film;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@Repository
public class SqlFilmDaoImpl implements FilmDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public int insertFilm(Film film) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(film);
        return film.getId();
    }

    @Override
    public Film getFilm(int id) {
        return entityManagerFactory.createEntityManager()
                .find(Film.class, id);
    }

    @Override
    public void deleteFilm(Film film) {
        entityManagerFactory.createEntityManager()
                .remove(film);
    }

    @Override
    public void updateFilm(Film film) {
        entityManagerFactory.createEntityManager()
                .refresh(film);
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<Film>(entityManagerFactory.createEntityManager()
                .createNamedQuery("Film.getAll").getResultList());

    }


}
