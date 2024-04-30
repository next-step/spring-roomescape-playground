package roomescape.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReservationVO {
    private Long id;
    @NotBlank
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    @NotNull
    private LocalTime time;

    public void setIdentifyKey(Long id){
        this.id = id;
    }
}
