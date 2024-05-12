package roomescape.model;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public static Reservation toEntity(Reservation reservation,Long id){
        return new Reservation(id,
                reservation.name,
                reservation.date,
                reservation.time
                );
    }
}
