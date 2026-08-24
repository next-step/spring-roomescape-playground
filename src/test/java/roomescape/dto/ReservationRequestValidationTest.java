package roomescape.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationRequestValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 예약자_이름이_비어있으면_검증에_실패한다() {
        ReservationRequest request = new ReservationRequest(" ", LocalDate.of(2030, 8, 5), LocalTime.of(15, 41));

        assertThat(validator.validate(request)).extracting(violation -> violation.getPropertyPath().toString())
                .contains("name");
    }

    @Test
    void 날짜가_null이면_검증에_실패한다() {
        ReservationRequest request = new ReservationRequest("브라운", null, LocalTime.of(15, 41));

        assertThat(validator.validate(request)).extracting(violation -> violation.getPropertyPath().toString())
                .contains("date");
    }

    @Test
    void 시간이_null이면_검증에_실패한다() {
        ReservationRequest request = new ReservationRequest("브라운", LocalDate.of(2030, 8, 5), null);

        assertThat(validator.validate(request)).extracting(violation -> violation.getPropertyPath().toString())
                .contains("time");
    }
}
