package step.learning.ioc;

import sun.security.provider.MD5;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Hash implements HashService{

    @Override
    public String getHash(String text) {
       try {
            MessageDigest ms = MessageDigest.getInstance("MD5");
            ms.update(text.getBytes());
            byte[] digest = ms.digest();
           return DatatypeConverter.printHexBinary(digest).toUpperCase();
       } catch (NoSuchAlgorithmException e) {
           System.out.printf(e.getMessage());
           return "-1";
       }
    }
}
