package roomescape.domain;


import lombok.*;

@Getter
@AllArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time time;
}
