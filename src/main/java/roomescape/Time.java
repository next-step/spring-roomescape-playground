package roomescape;


public class Time {
    private long id;
//    @NotBlank(message = "시간은 필수 입력 값입니다.")
    private String time;

    public Time(long id, String time) {
        this.id = id;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    //    public Time(long id, String name, String date, String time) {
//        this.id = id;
//        this.name = name;
//        this.date = date;
//        this.time = time;
//    }

}
