package ua.nure.ponomarev.enity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Bogdan_Ponamarev.
 */


@Entity
public enum PlaceStatus {
    FREE,
    BUSY,
    RESERVED;

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "place_status",unique = true,nullable = false)
    private String placeStatus;
}
