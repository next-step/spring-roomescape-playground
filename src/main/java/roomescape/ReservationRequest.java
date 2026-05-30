package roomescape;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    private String name;
    @NotNull(message = "날짜는 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "시간은 필수입니다.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    public ReservationRequest(){}

    public ReservationRequest(String name, LocalDate date, LocalTime time){

        this.name= name;
        this.date= date;
        this.time= time;
    }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }


}
