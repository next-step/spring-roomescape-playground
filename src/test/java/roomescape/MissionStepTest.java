package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.room.Room;
import roomescape.room.RoomRepository;
import roomescape.room.RoomResponseDTO;
import roomescape.time.Time;
import roomescape.time.TimeDAO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private TimeDAO timeDAO;

	@Test
	void 일단계() {
		RestAssured.given().log().all()
				.when().get("/")
				.then().log().all()
				.statusCode(200);
	}

	@Test
	void 이단계() {
		Time time = new Time(LocalTime.now());
		timeDAO.save(time);

		Room room1 = new Room("박한수", LocalDate.now(), time);
		Room room2 = new Room("홍길동", LocalDate.now(), time);
		Room room3 = new Room("브라운", LocalDate.now(), time);

		roomRepository.save(room1);
		roomRepository.save(room2);
		roomRepository.save(room3);

		RestAssured.given().log().all()
				.when().get("/reservations")
				.then().log().all()
				.body("size()", is(3));
	}

	@Test
	void 삼단계() {
		Time time = new Time(LocalTime.of(15, 40));
		timeDAO.save(time);

		Map<String, String> params = new HashMap<>();

		params.put("name", "브라운");
		params.put("date", "2023-08-05");
		params.put("time", time.getId().toString());

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
		Time time = new Time(LocalTime.of(15, 40));
		timeDAO.save(time);

		Map<String, String> params = new HashMap<>();

		params.put("name", "브라운");
		params.put("date", "2023-08-05");
		params.put("time", time.getId().toString());


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
		params.put("time", "15:40");

		RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(params)
				.when().post("/times")
				.then().log().all()
				.statusCode(201)
				.header("Location", "/times/1")
				.body("id", is(1));

		ValidatableResponse validatableResponse = RestAssured.given().log().all()
				.when().get("/times/1")
				.then().log().all()
				.statusCode(200);

		validatableResponse
				.body("time", is("15:40"));
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void 오단계() {
		try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
			assertThat(connection).isNotNull();
			assertThat(connection.getCatalog()).isEqualTo("DATABASE");
			assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void 육단계() {

		Time time = new Time(LocalTime.now());
		timeDAO.save(time);

		jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", time.getId());

		List<RoomResponseDTO.Read> reservations = RestAssured.given().log().all()
				.when().get("/reservations")
				.then().log().all()
				.statusCode(200).extract()
				.jsonPath().getList(".", RoomResponseDTO.Read.class);

		Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

		assertThat(reservations.size()).isEqualTo(count);
	}

	@Test
	void 칠단계() {
		Time time = new Time(LocalTime.now());
		timeDAO.save(time);

		Map<String, String> params = new HashMap<>();

		params.put("name", "브라운");
		params.put("date", "2023-08-05");
		params.put("time", time.getId().toString());

		RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(params)
				.when().post("/reservations")
				.then().log().all()
				.statusCode(201)
				.header("Location", "/reservations/1");

		Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
		assertThat(count).isEqualTo(1);

		RestAssured.given().log().all()
				.when().delete("/reservations/1")
				.then().log().all()
				.statusCode(204);

		Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
		assertThat(countAfterDelete).isEqualTo(0);
	}
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
}
