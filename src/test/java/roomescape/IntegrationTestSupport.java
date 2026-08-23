package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestClockConfig.class)
abstract class IntegrationTestSupport {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setRestAssuredPort() {
        RestAssured.port = port;
    }
}
