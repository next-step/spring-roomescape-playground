package roomescape.model;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id, String name, String date, String time) {
        checkAllFields(id, name, date, time);
        this.id = id;
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

    private void checkAllFields(Long id, String name, String date, String time) {
        checkId(id);
        checkName(name);
        checkDate(date);
        checkTime(time);
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
    }

    private void checkName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
    }

    private void checkDate(String date) {
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Date is required");
        }
    }

    private void checkTime(String time) {
        if (time == null || time.isBlank()) {
            throw new IllegalArgumentException("Time is required");
        }
    }
}
