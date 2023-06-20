package com.lineate.testyourlexicon.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class Translation {

  private Integer id;
  private String translateFromWord;
  private String translateToWord;

}
