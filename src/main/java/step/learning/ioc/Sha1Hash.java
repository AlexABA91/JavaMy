package step.learning.ioc;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Hash implements HashService{
    @Override
    public String getHash(String text) {
        try {
            MessageDigest ms = MessageDigest.getInstance("SHA-1");
            ms.update(text.getBytes());
            byte[] digest = ms.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            System.out.printf(e.getMessage());
            return "-1";
        }
    }
}
