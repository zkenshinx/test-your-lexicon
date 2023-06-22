package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.QuestionDto;
import com.lineate.testyourlexicon.models.Translation;
import com.lineate.testyourlexicon.repositories.TranslationRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationService {

  private final TranslationRepository translationRepository;

  public QuestionDto getRandomQuestion(String translateFrom,
                                       String translateTo,
                                       int answerOptionsCount) {
    Translation singleTranslationDto =
        translationRepository.getRandomTranslation(translateFrom, translateTo);
    List<String> possibleAnswers = translationRepository
        .getRandomWordsFromLanguageNotHavingId(translateTo,
                                               singleTranslationDto.getId(),
                                               answerOptionsCount - 1);
    QuestionDto questionDto =
        new QuestionDto(singleTranslationDto.getTranslateFromWord(), possibleAnswers);
    questionDto.addAnswer(singleTranslationDto.getTranslateToWord());
    return questionDto;
  }

  public List<String> supportedLanguages() {
    return Arrays.asList("English", "Georgian", "Russian", "German");
  }
}
