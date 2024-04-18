package roomescape;

public class Time {
  private long id;
  private String time;
  public Time(String time) {
    this.time = time;
  }
  public Time (long id, String time) {
    this.id = id;
    this.time = time;
  }
  public long getId() {
    return this.id;
  }

  public String getTime() {
    return this.time;
  }


}
