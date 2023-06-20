package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.QuestionDto;
import com.lineate.testyourlexicon.dto.SingleTranslationDto;
import com.lineate.testyourlexicon.dto.SupportedLanguagesDto;
import com.lineate.testyourlexicon.repositories.LanguageRepository;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService {

  // TODO Remove after implementing user gameplay
  private static final int ANSWERS_OPTIONS_COUNT = 4;
  private final LanguageRepository languageRepository;

  public QuestionDto getRandomQuestion(String translateFrom, String translateTo) {
    SingleTranslationDto singleTranslationDto =
        languageRepository.getRandomTranslation(translateFrom, translateTo);
    List<String> possibleAnswers = languageRepository
        .getRandomWordsFromLanguageNotHavingId(translateTo,
                                             singleTranslationDto.getId(),
                                       ANSWERS_OPTIONS_COUNT - 1);
    QuestionDto questionDto =
        new QuestionDto(singleTranslationDto.getTranslateFrom(), possibleAnswers);
    questionDto.addAnswer(singleTranslationDto.getTranslateTo());
    return questionDto;
  }

  public List<String> supportedLanguages() {
    return Arrays.asList("English", "Georgian", "Russian", "German");
  }
}
