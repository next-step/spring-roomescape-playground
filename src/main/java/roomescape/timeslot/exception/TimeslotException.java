package roomescape.timeslot.exception;

public class TimeslotException extends RuntimeException {

    private final Object debugData;

    public TimeslotException(String messageForClient) {
        this(messageForClient, null);
    }

    public TimeslotException(String messageForClient, Object debugData) {
        super(messageForClient);
        this.debugData = debugData;
    }

    public Object getDebugData() {
        return debugData;
    }
}
