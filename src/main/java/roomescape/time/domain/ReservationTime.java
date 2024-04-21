package roomescape.time.domain;

public class ReservationTime {
    private Long id;
    private String time;

    public ReservationTime(Long id, String time){
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getReservationTime() {
        return time;
    }

    public static ReservationTime toEntity(ReservationTime reservationTime, Long id){
        return new ReservationTime(id, reservationTime.time);
    }
}
