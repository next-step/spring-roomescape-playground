package roomescape.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerTest {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	@Test
	void JDBC_커넥션_테스트() {
		try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
			assertThat(connection).isNotNull();
			assertThat(connection.getCatalog()).isEqualTo("DATABASE");
			assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void JDBC_예약조회_동기화() {
		jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05",
				"15:40");

		List<Reservation> reservations = RestAssured.given().log().all()
				.when().get("/reservations")
				.then().log().all()
				.statusCode(200).extract()
				.jsonPath().getList(".", Reservation.class);

		Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

		assertThat(reservations.size()).isEqualTo(count);
	}
}
