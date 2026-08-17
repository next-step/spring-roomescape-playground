package cholog;

import cholog.dto.response.MemberResponse;
import cholog.entity.Member;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CRUDTest {

    @LocalServerPort
    private int port;

    private static int memberCount = 0;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void create() {

        var response = RestAssured
                .given().log().all()
                .body(new Member("brown", 20))
                .contentType(ContentType.JSON)
                .when().post("/members")
                .then().log().all().extract();

        memberCount++;

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void read() {
        var response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/members")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", MemberResponse.class)).hasSize(memberCount);
    }

    @Test
    void update() {
        MemberResponse created = RestAssured
                .given().log().all()
                .body(new Member("brown", 20))
                .contentType(ContentType.JSON)
                .when().post("/members")
                .then().log().all().extract().as(MemberResponse.class);

        memberCount++;

        var response = RestAssured
                .given().log().all()
                .body(new Member("brown", 30))
                .contentType(ContentType.JSON)
                .when().put("/members/" + created.memberId())
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void delete() {
        MemberResponse created = RestAssured
                .given().log().all()
                .body(new Member("brown", 20))
                .contentType(ContentType.JSON)
                .when().post("/members")
                .then().log().all().extract().as(MemberResponse.class);

        memberCount++;

        var response = RestAssured
                .given().log().all()
                .when().delete("/members/" + created.memberId())
                .then().log().all().extract();

        memberCount--;

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}