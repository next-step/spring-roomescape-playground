package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) //스프링 서버를 실제 포트로 실행해서 테스트
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//각 테스트 메서드가 실행되기 전에 스프링 상태를 새로 초기화 > 각 1,2,3단계 테스트가 서로 영향을 주지 않음
public class MissionStepTest {

    @Test
    void 일단계() {
        RestAssured.given().log().all()//HTTP 요청 준비, 요청 정보를 콘솔에 전부 출력
                .when().get("/")
                .then().log().all()//응답 결과 전부 로그로 출력
                .statusCode(200);//응답 상태 코드가 200인지 확인
    }
    @Test
    void 이단계() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations") //예약 목록 조회 api
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0)); // 초기 데이터가 없으므로 0개인지 확인
    }

    @Test
    void 삼단계() {
        Map<String, String> params = new HashMap<>(); //예약 생성 요청에 보낼 데이터를 담을 Map을 만듦
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        // 생성 요청
        RestAssured.given().log().all()
                .contentType(ContentType.JSON) //보내는 데이터 형식이 JSON임을 알려줌
                .body(params) //params 데이터를 body에 담음
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1") //응답 헤더의 Location 값이 /reservations/1인지 확인
                .body("id", is(1)); //응답 body 안의 id 값이 1인지 확인

        // 목록 조회 확인
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        // 삭제 요청
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        // 삭제 후 목록 확인
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
}
