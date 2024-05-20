package roomescape;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {

    }

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public static Reservation toEntity(Reservation reservation, Long id) {
        if (reservation.getName().isEmpty() || reservation.getTime().isEmpty() || reservation.getDate().equals("")){
            throw new IllegalArgumentException("필요한 인자가 없습니다");
        }
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
