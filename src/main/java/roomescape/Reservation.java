package roomescape;

import jakarta.validation.constraints.NotBlank;
import org.thymeleaf.util.StringUtils;

public class Reservation {
    private long id;
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "날짜는 필수 입력 값입니다.")
    private String date;
    @NotBlank(message = "시간은 필수 입력 값입니다.")
    private String time;

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
