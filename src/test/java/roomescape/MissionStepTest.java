package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

	private final RoomRepository roomRepository = new RoomRepository();

	@BeforeEach
	void setUp() {
		roomRepository.clear();
	}

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
				.when().get("/reservations")
				.then().log().all()
				.body("size()", is(0));
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

		// 삭제할 예약이 없는 경우
		RestAssured.given().log().all()
				.when().delete("/reservations/1")
				.then().log().all()
				.statusCode(400);
	}

	@Test
	void 날짜의_형식은_YYYY_MM_DD_형식이어야_한다() {
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

		ValidatableResponse validatableResponse = RestAssured.given().log().all()
				.when().get("/reservations/1")
				.then().log().all()
				.statusCode(200);

		validatableResponse
				.body("date", is("2023-08-05"));
	}

	@Test
	void 시간의_형식은_HH_MM_형식이어야_한다() {
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

		ValidatableResponse validatableResponse = RestAssured.given().log().all()
				.when().get("/reservations/1")
				.then().log().all()
				.statusCode(200);

		validatableResponse
				.body("time", is("15:40"));
	}

}
