package roomescape.domain;

import java.util.List;

public class Times {
    private final List<Time> times;

    public Times(List<Time> times) {
        this.times = List.copyOf(times);
    }

    public List<Time> getTimes() {
        return times;
    }
}
