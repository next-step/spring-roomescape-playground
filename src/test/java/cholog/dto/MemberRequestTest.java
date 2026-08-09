package cholog.dto;

import cholog.dto.request.MemberRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MemberRequestTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("이름 필드는 null값을 허용하지 않는다")
    void nameMustNotBeNullInMemberRequest() {

        // given
        String name = null;
        Integer age = 20;

        MemberRequest request = new MemberRequest(name, age);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)

                .when()
                .post("/members")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("이름 필드는 비어있을 수 없다")
    void nameMustRequiredInMemberRequest() {

        // given
        String name = "";
        Integer age = 20;

        MemberRequest request = new MemberRequest(name, age);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)

                .when()
                .post("/members")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("이름 필드는 최대 20자를 초과할 수 없다")
    void nameCannotExceed20CharInMemberRequest() {

        // given
        String name = "abcdefghijklmnopqrstu";
        Integer age = 20;

        MemberRequest request = new MemberRequest(name, age);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)

                .when()
                .post("/members")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("나이 필드는 null값을 허용하지 않는다")
    void ageMustNotBeNullInMemberRequest() {

        // given
        String name = "Alice";
        Integer age = null;

        MemberRequest request = new MemberRequest(name, age);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)

                .when()
                .post("/members")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("나이 필드는 null값을 허용하지 않는다")
    void ageMustNotBeNegaticeInMemberRequest() {

        // given
        String name = "Alice";
        Integer age = -1;

        MemberRequest request = new MemberRequest(name, age);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)

                .when()
                .post("/members")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("멤버 생성 요청이 정상적으로 처리된 경우")
    void allValidatedInMemberRequest() {

        // given
        String name = "Alice";
        Integer age = 20;

        MemberRequest request = new MemberRequest(name, age);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)

                .when()
                .post("/members")

                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
