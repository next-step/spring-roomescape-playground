package roomescape.domain.time;

import static org.hamcrest.Matchers.is;
import static roomescape.helper.TestHelper.deleteMethodTest;
import static roomescape.helper.TestHelper.getMethodTest;
import static roomescape.helper.TestHelper.postMethodTest;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.RoomescapeApplicationTest;

public class TimeControllerTest extends RoomescapeApplicationTest {

    @Test
    @DisplayName("시간 관련 API 기능 테스트")
    public void 시간_관련_API_기능_테스트() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        postMethodTest(params, "/times", 201)
                .header("Location", "/times/1");

        getMethodTest("/times", 200)
                .body("size()", is(1));

        deleteMethodTest("/times", 1, 204);
    }
}
