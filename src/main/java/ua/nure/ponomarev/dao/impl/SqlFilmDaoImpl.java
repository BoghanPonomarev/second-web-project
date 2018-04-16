package ua.nure.ponomarev.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.ponomarev.dao.FilmDao;
import ua.nure.ponomarev.enity.Film;
import ua.nure.ponomarev.enity.User;
import ua.nure.ponomarev.holder.EntityManagerHolder;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@Repository
public class SqlFilmDaoImpl implements FilmDao {
    private EntityManagerHolder entityManagerHolder;

    @Override
    public int insertFilm(Film film) {
        EntityManager entityManager = entityManagerHolder.getEntityManager();
        entityManager.persist(film);
        return film.getId();
    }

    @Override
    public Film getFilm(int id) {
        return entityManagerHolder.getEntityManager()
                .find(Film.class, id);
    }

    @Override
    public void deleteFilm(Film film) {
        entityManagerHolder.getEntityManager()
                .remove(film);
    }

    @Override
    public void updateFilm(Film film) {
        entityManagerHolder.getEntityManager()
                .refresh(film);
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<Film>(entityManagerHolder.getEntityManager().createNamedQuery("Film.getAll").getResultList());

    }

    @Autowired
    public void setEntityManagerHolder(EntityManagerHolder entityManagerHolder) {
        this.entityManagerHolder = entityManagerHolder;
    }
}
