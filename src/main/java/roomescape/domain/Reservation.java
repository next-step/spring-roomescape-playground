package roomescape.domain;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Reservation {

    private static AtomicLong ID_INDEX = new AtomicLong(1);

    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(String name, String date, String time) {
        id = ID_INDEX.getAndIncrement();
        this.name = name;
        this.date = date;
        this.time = time;
    }

}
