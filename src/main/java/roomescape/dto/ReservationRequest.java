package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.exception.BadRequestException;

import java.time.LocalDate;

public class ReservationRequest {

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate date;

    private Long timeId;

    public ReservationRequest() {}

    public ReservationRequest(String name, LocalDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    @JsonProperty("time") //사용자가 예전 방식으로 time을 직접 지정해서 보냈을경우
    public void setTime(Object time) {
        // 들어온 값이 숫자가 아니라 문자열 형식이면 400 에러를 유도
        if (time instanceof String) {
            throw new BadRequestException("이제 시간은 문자열 형식을 지원하지 않습니다. 시간 ID를 입력해주세요.");
        }
        if (time instanceof Number) { //숫자가 들어온 경우
            this.timeId = ((Number) time).longValue();
            //Integer을 Long으로 바꿔줌
        }
    }

    @JsonProperty("timeId")
    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    // Getters
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public Long getTimeId() { return timeId; }
}
