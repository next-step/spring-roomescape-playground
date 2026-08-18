package roomescape;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("홈 요청 시 200 OK를 반환하는지 테스트")
    void test_home_요청시_200_OK를_반환하는지_테스트() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("/reservation 요청 시 reservation 화면을 반환하는지 테스트")
    void test_reservation_요청시_reservation_화면을_반환하는지_테스트() {
        RestAssured.given().log().all()
            .when().get("/reservation")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    @DisplayName("/reservations 요청 시 200 상태 코드와 JSON 형식의 응답을 반환하는지 테스트")
    void test_reservations_요청시_정상_응답을_반환하는지_테스트() {
        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("POST /reservations 요청 시 201 상태 코드를 반환하는지 테스트")
    void test_post_요청시_예약이_생성되는지_테스트() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(201);
    }

    @Test
    @DisplayName("DELETE /reservations/{id} 요청 시 204 상태 코드를 반환하는지 테스트")
    void test_delete_요청시_예약이_삭제되는지_테스트(){

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations");

        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(204);
    }

    @Test
    @DisplayName("POST /reservations 요청 시 필수값이 공백이면 400 상태 코드를 반환하는지 테스트")
    void test_post_요청시_필수값이_공백이면_400을_반환하는지_테스트() {
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
    }

    @Test
    @DisplayName("DELETE /reservations/{id} 요청 시 존재하지 않는 예약이면 404 상태 코드를 반환하는지 테스트")
    void test_delete_요청시_존재하지_않는_예약이면_404를_반환하는지_테스트(){
        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(404);
    }

    @Test
    @DisplayName("데이터베이스 및 reservation 테이블 연결 확인 테스트")
    void test_데이터베이스_연결과_테이블_존재를_확인하는지_테스트() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("예약 조회 API가 데이터베이스에 저장된 예약을 반환하는지 테스트")
    void test_예약_조회_API가_데이터베이스를_조회하는지_테스트(){
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

        List<Reservation> reservations = RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200).extract()
            .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }


}
