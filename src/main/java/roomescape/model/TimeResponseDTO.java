package roomescape.model;

import lombok.Data;

@Data
public class TimeResponseDTO {
    private int id;
    private String time;

    public static TimeResponseDTO MakingResponse(Time time) {
        TimeResponseDTO result = new TimeResponseDTO();
        result.setId(time.getId());
        result.setTime(time.getTime());
        return result;
    }
}
