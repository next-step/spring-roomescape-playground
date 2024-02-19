package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reservation {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return this.id;
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

    public static Reservation toEntity(Reservation reservation, Long id) {
        if (reservation == null || !reservation.isValid()) {
            throw new IllegalArgumentException("누락된 사항이 있습니다. 확인해주세요.");
        }
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public boolean isValid() {
        return isNotNullOrEmpty(name) && isNotNullOrEmpty(date) && isNotNullOrEmpty(time);
    }

    private boolean isNotNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
