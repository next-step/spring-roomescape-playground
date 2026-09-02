package roomescape;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import roomescape.exception.InvalidReservationException;

class ReservationRequestTest {

    @ParameterizedTest
    @MethodSource("invalidRequests")
    void 예약_요청값이_비어_있으면_전용_예외가_발생한다(
        String name,
        String date,
        String time
    ) {
        ReservationRequest request = new ReservationRequest(name, date, time);

        assertThatThrownBy(request::validate)
            .isInstanceOf(InvalidReservationException.class);
    }

    @Test
    void 유효한_예약_요청은_검증을_통과한다() {
        ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", "15:40");

        assertThatCode(request::validate)
            .doesNotThrowAnyException();
    }

    private static Stream<Arguments> invalidRequests() {
        return Stream.of(
            Arguments.of(null, "2023-08-05", "15:40"),
            Arguments.of("브라운", "", "15:40"),
            Arguments.of("브라운", "2023-08-05", " ")
        );
    }
}
