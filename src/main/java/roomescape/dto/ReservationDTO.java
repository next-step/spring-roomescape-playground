package roomescape.dto;

import roomescape.domain.Time;

public record ReservationDTO (
    String name,
    String date,
    Long time
){
    public ReservationDTO(String name, String date, Long time){
        this.name = name;
        this.date = date;
        this.time = time;

        if(name.isEmpty() || date.isEmpty() || time == null){
            throw new IllegalArgumentException("필요한 인자가 부족합니다!");
        }
    }
}
