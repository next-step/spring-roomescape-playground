package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimeControllerTest {
	@Test
	void 시간_필드_조회_성공() {
		Map<String, String> params = new HashMap<>();
		params.put("time", "10:00");

		RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(params)
				.when().post("/times")
				.then().log().all()
				.statusCode(201)
				.header("Location", "/times/1");

		RestAssured.given().log().all()
				.when().get("/times")
				.then().log().all()
				.statusCode(200)
				.body("size()", is(1));

		RestAssured.given().log().all()
				.when().delete("/times/1")
				.then().log().all()
				.statusCode(204);
	}

	@Test
	void 사전_정의_시간_조회_가능() {
		Map<String, String> reservation = new HashMap<>();
		reservation.put("name", "브라운");
		reservation.put("date", "2023-08-05");
		reservation.put("time", "10:00");

		RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(reservation)
				.when().post("/reservations")
				.then().log().all()
				.statusCode(400);
	}
}
