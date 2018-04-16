package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.enity.Film;
import ua.nure.ponomarev.enity.User;

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
