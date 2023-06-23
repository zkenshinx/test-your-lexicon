package com.lineate.testyourlexicon.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class Translation {

  private Long id;
  private String translateFromWord;
  private String translateToWord;

}
