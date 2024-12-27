 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Admin
 */
public class MD5Encode {
   
    public static String md5Encode(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        String encodeString ;
        MessageDigest msd = MessageDigest.getInstance("MD5");
        
        byte[] textBytes = text.getBytes("UTF-8");
        byte[] encodeBytes  =msd.digest(textBytes);
        
        BigInteger bigInt =new BigInteger(1,encodeBytes);
        encodeString = bigInt.toString(16);
        return encodeString;
    }
}
