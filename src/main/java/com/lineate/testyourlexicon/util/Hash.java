package com.lineate.testyourlexicon.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

  public static Long hashToLong(String input) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
      BigInteger bigInteger = new BigInteger(1, encodedHash);
      return bigInteger.longValue();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Long hashToLong(Long input) {
    return hashToLong(String.valueOf(input));
  }

}
