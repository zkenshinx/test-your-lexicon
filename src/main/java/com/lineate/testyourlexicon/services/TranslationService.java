package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.models.Question;
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

  public Question getRandomQuestion(String translateFrom,
                                    String translateTo,
                                    int answerOptionsCount) {
    Translation translation =
        translationRepository.getRandomTranslation(translateFrom, translateTo);
    List<String> possibleAnswers = translationRepository
        .getRandomWordsFromLanguageNotHavingId(translateTo,
                                               translation.getId(),
                                               answerOptionsCount - 1);
    Question question =
        new Question(translation.getId(),
                     translation.getTranslateFromWord(),
                     possibleAnswers);
    question.addAnswer(translation.getTranslateToWord());
    return question;
  }

  public List<String> supportedLanguages() {
    return Arrays.asList("english", "georgian", "russian", "german");
  }
}
