package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTwoTest {
  @Test
  void 일단계() {
    Map<String, String> params = new HashMap<>();
    params.put("email", "admin@email.com");
    params.put("password", "password");

    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/login")
        .then().log().all()
        .statusCode(200)
        .extract();

    String token = response.headers().get("Set-Cookie").getValue().split(";")[0].split("=")[1];
    assertThat(token).isNotBlank();

    ExtractableResponse<Response> checkResponse = RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .cookie("token", token)
        .when().get("/login/check")
        .then().log().all()
        .statusCode(200)
        .extract();

    assertThat(checkResponse.body().jsonPath().getString("name")).isEqualTo("어드민");
  }
}

