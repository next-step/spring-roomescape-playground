package roomescape.Domain;

import org.springframework.cglib.core.Local;

import java.time.LocalTime;

public class Time {
    private Long id;
    private LocalTime time;

    public Time()
    {}

    public Time(Long id, LocalTime time)
    {
        this.id = id;
        this.time = time;
    }

    public Time(Long id, String time)
    {
        this(id, LocalTime.parse(time));
    }

    public Time(String time)
    {
        this(null, LocalTime.parse(time));
    }

    public Long getId()
    {
        return id;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public boolean notEmpty() {
        return notNullOrEmpty(time.toString());
    }

    private boolean notNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
