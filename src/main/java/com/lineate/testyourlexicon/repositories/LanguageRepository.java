package com.lineate.testyourlexicon.repositories;

import com.lineate.testyourlexicon.dto.SingleTranslationDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LanguageRepository {

  private final JdbcTemplate jdbcTemplate;

  public SingleTranslationDto getRandomTranslation(String translateFrom, String translateTo) {
    String sqlQuery = String.format("SELECT id, %s AS translate_from, %s AS translate_to "
        + "FROM translations ORDER BY random() LIMIT 1", translateFrom, translateTo);
    return jdbcTemplate.query(sqlQuery,
      (resultSet, rowNum) -> SingleTranslationDto.builder()
        .id(resultSet.getInt("id"))
        .translateFrom(resultSet.getString("translate_from"))
        .translateTo(resultSet.getString("translate_to"))
        .build()
    ).get(0);
  }

  public List<String> getRandomWordsFromLanguageNotHavingId(String language, int id, int count) {
    String sqlQuery = String.format("SELECT %s AS language FROM translations " +
        "WHERE id != %s ORDER BY random() LIMIT %s", language, id, count);
    return jdbcTemplate.query(
      sqlQuery,
      (resultSet, rowNum) -> resultSet.getString("language")
    );
  }
}
