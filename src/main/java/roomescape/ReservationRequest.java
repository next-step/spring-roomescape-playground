package roomescape;

import jakarta.validation.constraints.NotBlank;

public class ReservationRequest {

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    private String name;
    @NotBlank(message = "날짜는 비어있을 수 없습니다.")
    private String date;
    @NotBlank(message = "시간은 비어있을 수 없습니다.")
    private String time;

    public ReservationRequest(){}

    public ReservationRequest(String name, String date, String time){

        this.name= name;
        this.date= date;
        this.time= time;
    }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }


}
