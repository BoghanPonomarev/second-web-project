package ua.nure.ponomarev.model.dao;

import ua.nure.ponomarev.model.enity.Cinema;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface CinemaDao {
    int insertCinema(Cinema cinema);

    Cinema getCinema(int id);

    void deleteCinema(Cinema cinema);

    void updateCinema(Cinema cinema);

    List<Cinema> getAllCinema();
}
