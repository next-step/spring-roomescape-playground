package roomescape.domain.dto;

<<<<<<< HEAD
import jakarta.validation.constraints.NotNull;
=======
>>>>>>> {boya-go}/boya-go
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

<<<<<<< HEAD
    @NotNull(message = "이름은 필수입니다.")
    private String name;
    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate date;
    @NotNull(message = "시간은 필수입니다.")
    private LocalTime time;
=======
    private final String name;
    private final LocalDate date;
    private final LocalTime time;
>>>>>>> {boya-go}/boya-go

    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
