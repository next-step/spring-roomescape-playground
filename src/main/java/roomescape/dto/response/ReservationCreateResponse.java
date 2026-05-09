package roomescape.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationCreateResponse {
    @NotNull(message = "아이디가 설정되지 않았습니다.")
    private Long id;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotNull(message = "날짜은 필수 입력값입니다.")
    private LocalDate date;

    @NotNull(message = "시간은 필수 입력값입니다.")
    private LocalTime time;

    public static ReservationCreateResponse from(Reservation reservation) {
        return ReservationCreateResponse.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
    }
}
