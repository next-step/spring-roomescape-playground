package roomescape.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reservation {
    private Long id;
    private String name;
    private LocalDateTime dateTime;

}