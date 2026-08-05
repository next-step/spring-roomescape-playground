package cholog.entity;

import cholog.Member;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MemberTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("수정 이후에도 같은 객체인지 비교")
    void isEqualMemberAfterUpdate() {
        // given
        Member before = new Member("Alice", 20);
        Member after = new Member("Bob", 21);

        // when
        Member created = RestAssured
                .given().log().all()
                .body(before)
                .contentType(ContentType.JSON)
                .when().post("/members")
                .then().log().all().extract().body().as(Member.class);

        Member updated = RestAssured
                .given().log().all()
                .body(after)
                .contentType(ContentType.JSON)
                .when().put("/members/" + created.getId())
                .then().log().all().extract().body().as(Member.class);

        // then
        assertThat(created.getId()).isEqualTo(updated.getId());
        assertThat(updated.getName()).isEqualTo(after.getName());
        assertThat(updated.getAge()).isEqualTo(after.getAge());
    }
}
