package roomescape;

public class RoomEscapeReservationDomain {
    private final long id;
    private final String content;
    public RoomEscapeReservationDomain(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
