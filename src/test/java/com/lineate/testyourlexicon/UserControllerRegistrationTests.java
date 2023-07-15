package com.lineate.testyourlexicon;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.lineate.testyourlexicon.util.JsonUtil.objectToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerRegistrationTests {

  private static final String REGISTRATION_URL = "/api/users";

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserRepository userRepository;
  private UserRegistrationDto userRegistrationDto;

  @BeforeEach
  public void setUp() {
    // Set ups a valid user
    userRegistrationDto = new UserRegistrationDto();
    userRegistrationDto.setFirstName("Ken");
    userRegistrationDto.setLastName("Kaneki");
    userRegistrationDto.setEmail("validemail@gmail.com");
    userRegistrationDto.setPassword("password123");
    userRegistrationDto.setConfirmationPassword("password123");
  }

  @ParameterizedTest
  @ValueSource(strings = {"notvalid123name", "spaces not allowed", "1234", "სახელი"})
  public void whenIncorrectFirstNameProvided_thenExpectBadRequest(String firstName) throws Exception {
    userRegistrationDto.setFirstName(firstName);
    mockMvc.perform(post(REGISTRATION_URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectToJson(userRegistrationDto)))
      .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"notvalid123name", "spaces not allowed", "1234", "გვარი"})
  public void whenIncorrectLastNameProvided_thenExpectBadRequest(String lastName) throws Exception {
    userRegistrationDto.setLastName(lastName);
    mockMvc.perform(post(REGISTRATION_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectToJson(userRegistrationDto)))
      .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"invalidemail", "gmail@gmail.", "@gmail.com", "gmail@.com"})
  public void whenIncorrectEmailProvided_thenExpectBadRequest(String email) throws Exception {
    userRegistrationDto.setEmail(email);
    mockMvc.perform(post(REGISTRATION_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectToJson(userRegistrationDto)))
      .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"onlyletters", "short1", "12345678"})
  public void whenIncorrectPasswordProvided_thenExpectBadRequest(String password) throws Exception {
    userRegistrationDto.setPassword(password);
    mockMvc.perform(post(REGISTRATION_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectToJson(userRegistrationDto)))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void whenPasswordsDoNotMatch_thenExpectBadRequest() throws Exception {
    userRegistrationDto.setPassword("notmatchingpassword123");
    mockMvc.perform(post(REGISTRATION_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectToJson(userRegistrationDto)))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void whenEverythingProvidedIsValid_thenExpectStatusCreated() throws Exception {
    mockMvc.perform(post(REGISTRATION_URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectToJson(userRegistrationDto)))
      .andExpect(status().isCreated());
  }
}
