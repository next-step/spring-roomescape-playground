package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalTime;
//시간 생성 요청 dto
public class TimeRequest {
    @NotNull(message = "시간은 필수 입력 값입니다.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
    //LocalTime 타입 10:00을 LocalTime.of(10.0)으로 변환해줌

    public TimeRequest() {}

    public TimeRequest(LocalTime time) { this.time = time; }

    public LocalTime getTime() { return time; }
}
