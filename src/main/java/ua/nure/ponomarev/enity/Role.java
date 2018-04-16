package ua.nure.ponomarev.enity;

import javax.persistence.*;

/**
 * @author Bogdan_Ponamarev.
 */
@Entity

public enum  Role{
    ANONYMOUS,
    USER,
    ADMIN;

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "role_name")
    private String roleName;


}
