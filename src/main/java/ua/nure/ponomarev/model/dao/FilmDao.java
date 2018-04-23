package ua.nure.ponomarev.model.dao;

import ua.nure.ponomarev.model.enity.Film;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface FilmDao {

    int insertFilm(Film film);

    Film getFilm(int id);

    void deleteFilm(Film film);

    void updateFilm(Film film);

    List<Film> getAllFilms();
}
