package com.lineate.testyourlexicon.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SingleTranslationDto {

  private Integer id;
  private String translateFrom;
  private String translateTo;

}
