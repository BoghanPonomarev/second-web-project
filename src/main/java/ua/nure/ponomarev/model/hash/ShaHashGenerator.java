package ua.nure.ponomarev.model.hash;

import com.google.common.hash.Hashing;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Bogdan_Ponamarev.
 */
@Component
public class ShaHashGenerator implements HashGenerator {
    @Override
    public String generateHash(String lineToHashGeneration, String salt) {
        return Hashing.sha256()
                .hashString(lineToHashGeneration+salt, StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public String getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[10];
        random.nextBytes(bytes);
        return Base64.encode(bytes);
    }

}
