package com.lineate.testyourlexicon.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

  public static String objectToJson(Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

}
