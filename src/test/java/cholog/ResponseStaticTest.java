package cholog;

import static org.assertj.core.api.Assertions.assertThat;

import hello.DemoApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = DemoApplication.class)

class ResponseStaticTest {
    @Test
    void responseIndexPage() {
        var response = RestAssured
                .given().log().all()
                .when().get("/hi.html")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    void responseStaticPage() {
        var response = RestAssured
                .given().log().all()
                .when().get("/hello.html")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

