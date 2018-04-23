package ua.nure.ponomarev.model.dao;

import ua.nure.ponomarev.model.enity.Hall;

/**
 * @author Bogdan_Ponamarev.
 */
public interface HallDao {
    int insertHall(Hall hall);

    Hall getHall(int id);

    void deleteHall(Hall hall);

    void updateHall(Hall hall);
}
