package roomescape.reservation;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.ReservationId;
import roomescape.reservation.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationScenarioTest {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	Map<String, String> createParams;
	ReservationResponse expected;
	
	ReservationScenarioTest() {
		createParams = new HashMap<>();
		createParams.put("name", "브라운");
		createParams.put("date", LocalDate.now().plusDays(5).format(DateTimeFormatter.ISO_LOCAL_DATE));
		createParams.put("time", "15:40");
		
		expected = new ReservationResponse(
				new ReservationId(1),
				createParams.get("name"),
				LocalDate.parse(createParams.get("date")),
				LocalTime.parse(createParams.get("time"))
		);
		
		RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
	}
	
	@Test
	@Order(1)
	void 예약_생성() {
		ReservationResponse created = RestAssured
				.given().contentType(ContentType.JSON).body(createParams)
				.when().post("/reservations")
				.then()
				.statusCode(201)
				.header("Location", "/reservations/1")
				.extract().as(ReservationResponse.class);
		
		assertThat(created).isEqualTo(expected);
	}
	
	@Test
	@Order(2)
	void 예약_중복_추가_불가능() {
		RestAssured.given().contentType(ContentType.JSON).body(createParams)
				.when().post("/reservations")
				.then().statusCode(409);
	}
	
	@Test
	@Order(3)
	void 예약_조회() {
		ReservationResponse[] current = RestAssured.given()
				.when().get("/reservations")
				.then()
				.statusCode(200)
				.extract().as(ReservationResponse[].class);
		
		assertThat(current)
				.singleElement()
				.isEqualTo(expected);
	}
	
	@Test
	@Order(4)
	void 예약_삭제() {
		int previousCount = getReservationsCount();
		
		RestAssured.given()
				.when().delete("/reservations/1")
				.then()
				.statusCode(204);
		
		RestAssured.given()
				.when().get("/reservations")
				.then()
				.statusCode(200)
				.body("size()", is(0));
		
		assertThat(getReservationsCount())
				.isEqualTo(previousCount - 1);
	}
	
	@Test
	@Order(5)
	void 존재하지_않는_예약_삭제_불가능() {
		RestAssured.given()
				.when().delete("/reservations/1")
				.then().statusCode(404);
	}
	
	
	private int getReservationsCount() {
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reservations", Integer.class);
		return Objects.requireNonNull(count);
	}
}
