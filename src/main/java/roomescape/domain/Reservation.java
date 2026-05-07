package roomescape.domain;

import roomescape.exception.BadRequestException;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id, String name, String date, String time) {
        validateName(name);
        validateRequired(date, "날짜");
        validateRequired(time, "시간");

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateName(String name) {
        validateRequired(name, "이름");

        if (name.length() > 10) {
            throw new BadRequestException("이름은 10자 이하만 가능합니다.");
        }
    }

    private void validateRequired(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(fieldName + "이 존재하지 않습니다.");
        }
    }

    public boolean isSameSchedule(Reservation other) {
        return this.date.equals(other.date)
                && this.time.equals(other.time);
    }

    public Long getId() {
        return id;
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
