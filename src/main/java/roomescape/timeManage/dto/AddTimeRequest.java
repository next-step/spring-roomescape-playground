package roomescape.timeManage.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;

public class AddTimeRequest {
    @NotBlank(message = "시간은 필수 값입니다.")
    final private String time;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AddTimeRequest(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
