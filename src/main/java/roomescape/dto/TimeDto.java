package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class TimeDto {
    private Long id;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    public void setIdentifyKey(Long id){
        this.id = id;
    }

}
