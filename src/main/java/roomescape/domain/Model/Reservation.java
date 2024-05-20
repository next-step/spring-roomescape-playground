package roomescape.domain.Model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Reservation {
    private Long id;
    private String name;
    private LocalDateTime localDateTime;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDateTime localDateTime) {
        this.id = id;
        this.name = name;
        this.localDateTime = localDateTime;
    }
    public Reservation(String name, LocalDateTime localDateTime) {
        this.name = name;
        this.localDateTime = localDateTime;
    }
}
