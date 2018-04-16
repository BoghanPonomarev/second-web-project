package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.enity.Film;
import ua.nure.ponomarev.enity.Hall;

/**
 * @author Bogdan_Ponamarev.
 */
public interface HallDao {
    int insertHall(Hall hall);

    Hall getHall(int id);

    void deleteHall(Hall hall);

    void updateHall(Hall hall);
}
