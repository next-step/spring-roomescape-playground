package roomescape.pages;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PagesTest {
	@Test
	void 프론트_페이지에_접속_가능() {
		RestAssured.given()
				.when().get("/")
				.then().statusCode(200);
		
		RestAssured.given()
				.when().get("/reservation")
				.then().statusCode(200);
	}
}
