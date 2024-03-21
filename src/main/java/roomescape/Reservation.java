package roomescape;
//엔티티를 작성하여 확인 할 수 있게 했다 다음에는 롬복으로 진행해서 해당과정을 생략할 계획이다
public class Reservation {
    private long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
