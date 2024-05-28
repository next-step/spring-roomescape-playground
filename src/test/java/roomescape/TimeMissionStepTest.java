package roomescape;

import static org.hamcrest.core.Is.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class TimeMissionStepTest {
	@Test
	void 팔단계() {
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
	void 구단계() {
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
