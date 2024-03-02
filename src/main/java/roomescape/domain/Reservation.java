package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private String time;

//    public Reservation() {
//
//    }

    @JsonCreator
    public Reservation(@JsonProperty("id") Long id,
                       @JsonProperty("name") String name,
                       @JsonProperty("date") String date,
                       @JsonProperty("time") String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        if (reservation == null) {
            throw new IllegalArgumentException("누락된 사항이 있습니다. 확인해주세요.");
        }
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public boolean isValid(String name, String date, String time) {
        return isNotNullOrEmpty(name) && isNotNullOrEmpty(date) && isNotNullOrEmpty(time);
    }

    private boolean isNotNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

}
