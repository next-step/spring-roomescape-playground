package roomescape.DTO;

import roomescape.Model.Time;

public record SaveTimeRequest(String time) {
    public SaveTimeRequest{
        validateBlank(time);
    }

    private void validate(String time) {
        validateBlank(time);
    }

    private void validateBlank(String time) {
        if (time.isBlank()) {
            throw new IllegalArgumentException("값이 입력되지 않았습니다");
        }
    }

    public Time toTime() {
        return new Time(time);
    }
}
