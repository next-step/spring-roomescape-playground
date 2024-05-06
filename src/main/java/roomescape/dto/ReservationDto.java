package roomescape.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class ReservationRequest{
        @NotBlank
        private String name;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        @NotNull
        private Long time;
    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class ReservationResponse{
        private Long id;
        private String name;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        private TimeDto time;

        public  void setIdentifyKey(Long id){
            this.id = id;
        }
    }









}
