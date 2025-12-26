package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationResponse;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class SpringMessionStep {
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

    @Test
    void 이단계() {
        String token = createToken("admin@email.com", "password");  // 일단계에서 토큰을 추출하는 로직을 메서드로 따로 만들어서 활용하세요.

        Map<String, Object> params = new HashMap<>();
        params.put("date", "2026-03-01");
        params.put("timeId", 1);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(params)
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.as(ReservationResponse.class).name()).isEqualTo("어드민");

        params.put("name", "브라운");
        params.put("timeId", 2);  // 중복 방지를 위해 다른 시간으로 변경

        ExtractableResponse<Response> adminResponse = RestAssured.given().log().all()
                .body(params)
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .post("/reservations")
                .then().log().all()
                .extract();

        assertThat(adminResponse.statusCode()).isEqualTo(201);
        assertThat(adminResponse.as(ReservationResponse.class).name()).isEqualTo("브라운");
    }

    // 일단계에서 토큰을 추출하는 로직을 메서드로 분리
    private String createToken(String email, String password) {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("email", email);
        loginParams.put("password", password);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginParams)
                .when().post("/login")
                .then().log().all()
                .statusCode(200)
                .extract();

        return response.headers().get("Set-Cookie").getValue().split(";")[0].split("=")[1];
    }
}
