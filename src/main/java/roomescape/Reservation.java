package roomescape;

public class Reservation {
  private int id;
  private String name;
  private String date;
  private String time;

  public Reservation(int id, String name, String date, String time) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
  }

  public int getId() {
    return this.id;
  }
  public String getName() {
    return this.name;
  }
  public String getDate() {
    return this.date;
  }
  public String getTime() {
    return this.time;
  }

  public static Reservation toEntity(Reservation reservation, int id) {
    return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
  }
}
