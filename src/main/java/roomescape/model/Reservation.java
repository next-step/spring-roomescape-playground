package roomescape.model;

public record Reservation (int id, String name, String date, String time) {
    public Reservation copy() {
        return new Reservation(this.id, this.name, this.date, this.time);
    }
}
