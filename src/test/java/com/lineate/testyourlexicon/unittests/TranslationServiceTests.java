package com.lineate.testyourlexicon.unittests;


import com.lineate.testyourlexicon.models.Question;
import com.lineate.testyourlexicon.models.Translation;
import com.lineate.testyourlexicon.repositories.TranslationRepository;
import com.lineate.testyourlexicon.services.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TranslationServiceTests {

  private TranslationService translationService;
  private TranslationRepository translationRepository;

  @BeforeEach
  private void setUp() {
    translationRepository = mock(TranslationRepository.class);
    translationService = new TranslationService(translationRepository);
  }

  @Test
  public void testTranslationServiceGetRandomQuestion() {
    Translation translation = Translation.builder()
      .id(0)
      .translateFromWord("sea")
      .translateToWord("seaTranslation")
      .build();
    when(translationRepository.getRandomTranslation("english", "georgian"))
      .thenReturn(translation);
    when(translationRepository.getRandomWordsFromLanguageNotHavingId("georgian", 0, 3))
      .thenReturn(new ArrayList<String>(List.of("notSea", "notSea2", "notSea3")));

    Question questionDto =
      translationService.getRandomQuestion("english", "georgian", 4);
    assertThat(questionDto.getWord()).isEqualTo("sea");
    assertThat(questionDto.getAnswerOptions()).hasSize(4);
    assertThat(questionDto.getAnswerOptions().contains("seaTranslation")).isTrue();
  }
}
