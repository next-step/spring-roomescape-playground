package roomescape.dto;

public class Reservation {

    // 객체 정보
    private Long id;
    private String name;
    private String date;
    private String time;

    // 생성자
    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    // getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
