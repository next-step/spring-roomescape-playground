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
public class MvcMissionStepTest {

	@Test
	void 일단계() {
		RestAssured.given().log().all()
			.when().get("/")
			.then().log().all()
			.statusCode(200);
	}

	@Test
	void 이단계() {
		RestAssured.given().log().all()
			.when().get("/reservation")
			.then().log().all()
			.statusCode(200);

		RestAssured.given().log().all()
			.when().get("/reservations")
			.then().log().all()
			.statusCode(200)
			.body("size()", is(0)); // 아직 생성 요청이 없으니 Controller에서 임의로 넣어준 Reservation 갯수 만큼 검증하거나 0개임을 확인하세요.
	}

	@Test
	void 삼단계() {
		Map<String, String> params = new HashMap<>();
		params.put("name", "브라운");
		params.put("date", "2023-08-05");
		params.put("time", "15:40");

		RestAssured.given().log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.when().post("/reservations")
			.then().log().all()
			.statusCode(201)
			.header("Location", "/reservations/1")
			.body("id", is(1));

		RestAssured.given().log().all()
			.when().get("/reservations")
			.then().log().all()
			.statusCode(200)
			.body("size()", is(1));

		RestAssured.given().log().all()
			.when().delete("/reservations/1")
			.then().log().all()
			.statusCode(204);

		RestAssured.given().log().all()
			.when().get("/reservations")
			.then().log().all()
			.statusCode(200)
			.body("size()", is(0));
	}

	@Test
	void 사단계() {
		Map<String, String> params = new HashMap<>();
		params.put("name", "브라운");
		params.put("date", "");
		params.put("time", "");

		// 필요한 인자가 없는 경우
		RestAssured.given().log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.when().post("/reservations")
			.then().log().all()
			.statusCode(400);

		RestAssured.given().log().all()
			.when().delete("/reservations/1")
			.then().log().all()
			.statusCode(400);
	}

	@Test
	void 날짜_형식이_잘못되면_예외를_발생시킨다() {
		Map<String, String> params = new HashMap<>();
		params.put("name", "브라운");
		params.put("date", "20-05-25");
		params.put("time", "12:05");

		RestAssured.given().log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.when().post("/reservations")
			.then().log().all()
			.statusCode(400);
	}

	@Test
	void 시간_형식이_잘못되면_예외를_발생시킨다() {
		Map<String, String> params = new HashMap<>();
		params.put("name", "브라운");
		params.put("date", "2024-05-25");
		params.put("time", "12:5");

		RestAssured.given().log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.when().post("/reservations")
			.then().log().all()
			.statusCode(400);
	}
}
