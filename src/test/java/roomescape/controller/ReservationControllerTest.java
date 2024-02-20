package roomescape.controller;


import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.repository.ReservationRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerTest {

	@Autowired
	private ReservationRepository reservationRepository;

	@BeforeEach
	void setUp() {
		reservationRepository = new ReservationRepository();
	}

	@Test
	void 예약_관리_페이지_접근() {
		RestAssured.given().log().all()
				.when().get("/reservation")
				.then().log().all()
				.statusCode(200);
	}

	@Test
	void 예약_조회_성공() {
		RestAssured.given().log().all()
				.when().get("/reservations")
				.then().log().all()
				.statusCode(200)
				.body("size()", is(0));
	}

	@Test
	void 예약_추가_성공() {
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
	void 예약_삭제_입력_검증() {
		Map<String, String> params = new HashMap<>();
		params.put("name", "브라운");
		params.put("date", "");
		params.put("time", "");

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
}
