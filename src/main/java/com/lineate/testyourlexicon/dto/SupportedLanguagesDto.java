package com.lineate.testyourlexicon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public record SupportedLanguagesDto(List<String> languages) {

}
