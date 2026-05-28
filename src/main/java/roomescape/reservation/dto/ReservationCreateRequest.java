package roomescape.reservation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Date;


public record ReservationCreateRequest(
        @NotBlank(message = "이름은 비어 있을 수 없습니다.")
        @Size(max = 255, message = "이름은 255자를 넘을 수 없습니다.")
        String name,

        @NotNull(message = "날짜는 필수입니다.")
        @Future(message = "예약 날짜는 미래여야 합니다.")
        Date date,

        @NotNull(message = "시간은 필수입니다.")
        Long time
) { }
