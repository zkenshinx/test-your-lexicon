package com.lineate.testyourlexicon.repositories;

import com.lineate.testyourlexicon.models.Translation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TranslationRepository {

  private final JdbcTemplate jdbcTemplate;

  public Translation getRandomTranslation(String translateFrom, String translateTo) {
    String sqlQuery = String.format("SELECT id, %s AS translate_from, %s AS translate_to "
        + "FROM translations ORDER BY random() LIMIT 1", translateFrom, translateTo);
    return jdbcTemplate.query(sqlQuery,
      (resultSet, rowNum) -> Translation.builder()
        .id(resultSet.getLong("id"))
        .translateFromWord(resultSet.getString("translate_from"))
        .translateToWord(resultSet.getString("translate_to"))
        .build()
    ).get(0);
  }

  public List<String> getRandomWordsFromLanguageNotHavingId(String language, long id, int count) {
    String sqlQuery = String.format("SELECT %s AS language FROM translations "
        + "WHERE id != %s ORDER BY random() LIMIT %s", language, id, count);
    return jdbcTemplate.query(
      sqlQuery,
      (resultSet, rowNum) -> resultSet.getString("language")
    );
  }

  public String languageDefinitionGivenIdAndLanguage(Long id, String language) {
    String sqlQuery = String.format("SELECT %s AS language FROM translations "
       + "WHERE id = %s", language, id);
    return jdbcTemplate.query(
      sqlQuery,
      (resultSet, rowNum) -> resultSet.getString("language")
    ).get(0);
  }
}
