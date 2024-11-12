package roomescape.model;

import roomescape.exception.InvalidReservationException;

public class ReservationRequest {
    private String name;
    private String date;
    private String time;

    public ReservationRequest() {}

    public ReservationRequest(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    // 검증 메서드 추가
    public void validate() {
        if (name == null || name.isEmpty() ||
                date == null || date.isEmpty() ||
                time == null || time.isEmpty()) {
            throw new InvalidReservationException("필요한 인자가 없습니다.");
        }
    }
}
