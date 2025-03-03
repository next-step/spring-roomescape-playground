package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Time;
import roomescape.dto.request.CreateTimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeControllerTest {

    @Autowired
    TimeRepository timeRepository;

    @Test
    void 시간을_생성할_수_있다() {
        CreateTimeRequest request = new CreateTimeRequest(LocalTime.of(13, 0));

        TimeResponse response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/times/1")
                .extract()
                .as(TimeResponse.class);

        List<Time> times = timeRepository.findAll();

        assertAll(
                () -> assertThat(response.time()).isEqualTo(request.time()),
                () -> assertThat(times).hasSize(1)
        );
    }

    @Test
    void 해당시간이_이미_존재하면_시간_생성에_실패한다() {
        CreateTimeRequest request = new CreateTimeRequest(LocalTime.of(13, 0));
        timeRepository.save(request.toTime());

        CreateTimeRequest targetRequest = new CreateTimeRequest(LocalTime.of(13, 0));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(targetRequest)
                .when().post("/times")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 생성된_모든_시간을_조회할_수_있다() {
        CreateTimeRequest request1 = new CreateTimeRequest(LocalTime.of(13, 0));
        timeRepository.save(request1.toTime());
        CreateTimeRequest request2 = new CreateTimeRequest(LocalTime.of(14, 0));
        timeRepository.save(request2.toTime());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when().get("/times")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("$.size()", is(2));
    }

    @Test
    void 특정_시간을_삭제할_수_있다() {
        CreateTimeRequest request = new CreateTimeRequest(LocalTime.of(13, 0));
        timeRepository.save(request.toTime());

        RestAssured.given()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 해당시간이_존재하지_않으면_시간_삭제에_실패한다() {
        RestAssured.given()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
