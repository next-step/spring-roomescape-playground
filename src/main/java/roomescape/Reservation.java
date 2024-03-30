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

  public Reservation() {

  }
}
