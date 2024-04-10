package roomescape;

public class Reservation {
  private Long id;
  private String name;
  private String date;
  private String time;

  public Reservation() {}
  public Reservation(String name, String date, String time) {
    this.name = name;
    this.date = date;
    this.time = time;
  }


  public Long getId() {
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

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
