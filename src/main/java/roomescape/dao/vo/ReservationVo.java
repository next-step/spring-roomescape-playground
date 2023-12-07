package roomescape.dao.vo;

public class ReservationVo {
    private Long reservation_id;
    private String name;
    private String date;
    private Long time_id;
    private String time_value;

    public ReservationVo() {
    }

    public ReservationVo(Long reservation_id, String name, String date, Long time_id, String time_value) {
        this.reservation_id = reservation_id;
        this.name = name;
        this.date = date;
        this.time_id = time_id;
        this.time_value = time_value;
    }

    public Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTime_id() {
        return time_id;
    }

    public void setTime_id(Long time_id) {
        this.time_id = time_id;
    }

    public String getTime_value() {
        return time_value;
    }

    public void setTime_value(String time_value) {
        this.time_value = time_value;
    }
}
