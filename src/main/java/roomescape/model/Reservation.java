package roomescape.model;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Getter
public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = Exception.validateNameIsNotNull(name);
        this.date = Exception.validateDateIsNotNull(date);
        this.time = Exception.validateTimeIsNotNull(time);
    }

    public static Reservation toEntity(Reservation reservation, Long id){
        return new Reservation(id,
                reservation.name,
                reservation.date,
                reservation.time
                );
    }
}
