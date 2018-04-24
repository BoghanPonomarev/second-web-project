package ua.nure.ponomarev.model.hash;

import java.security.NoSuchAlgorithmException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface HashGenerator {

    String generateHash(String lineToHashGeneration,String salt);

    String getRandomSalt();
}
