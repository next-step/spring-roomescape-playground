package roomescape.controller;

import static roomescape.utils.ErrorMessage.EMPTY_DATE_ERROR;
import static roomescape.utils.ErrorMessage.EMPTY_NAME_ERROR;
import static roomescape.utils.ErrorMessage.EMPTY_TIME_ERROR;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import roomescape.domain.Time;

@Getter
public class ReservationForm {
    private Long id;
    @NotEmpty(message = EMPTY_NAME_ERROR)
    private String name;
    @NotEmpty(message = EMPTY_DATE_ERROR)
    private String date;
    @NotNull(message = EMPTY_TIME_ERROR)
    private Long time;
}
