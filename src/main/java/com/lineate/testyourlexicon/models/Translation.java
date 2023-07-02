package com.lineate.testyourlexicon.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Translation {

  private Long id;
  private String translateFromWord;
  private String translateToWord;

}
