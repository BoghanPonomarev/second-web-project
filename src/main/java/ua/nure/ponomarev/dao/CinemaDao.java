package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.enity.Cinema;
import ua.nure.ponomarev.enity.User;

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
