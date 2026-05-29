package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "날짜는 필수입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "시간 ID는 필수입니다.")
    private Long timeId;

    public ReservationRequest() {
    }

    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public Long getTimeId() { return timeId; }
}
