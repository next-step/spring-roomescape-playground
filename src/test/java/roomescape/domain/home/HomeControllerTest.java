package roomescape.domain.home;

import static roomescape.helper.TestHelper.getMethodTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.RoomescapeApplicationTest;

public class HomeControllerTest extends RoomescapeApplicationTest {

    @Test
    @DisplayName("기본 페이지 요청 시 200 OK를 반환한다.")
    public void 기본_페이지_요청_시_200_OK를_반환한다() {
        getMethodTest("/", 200);
    }
}
