package roomescape.Model;

import roomescape.Exception.BadRequestReservationException;

public class RequestReservationDTO {

    private String name;
    private String date;
    private int time; // 수정된 부분: String에서 int로 변경

    public RequestReservationDTO(String name, String date, int time) {
        this.name = name;
        this.date = date;
        this.time = time;
        validateReservation();
    }

    public void validateReservation() {
        if (name.isEmpty() || date.isEmpty() || time <= 0)
            throw new BadRequestReservationException("이름, 날짜, 시간을 모두 입력하세요.");
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }
}
