package roomescape.domain;

import roomescape.dto.request.ReservationRequest;

public class ReservationTime {
    private final String name;
    private final String date;
    private final String time;

    public ReservationTime(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationTime from(ReservationRequest reservationRequest) {
        return new ReservationTime(reservationRequest.getName(), reservationRequest.getDate(), reservationRequest.getTime());
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
}
