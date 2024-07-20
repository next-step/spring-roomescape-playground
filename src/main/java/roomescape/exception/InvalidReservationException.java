package roomescape.exception;

public class InvalidReservationException extends RuntimeException{
  
    private String fieldName;

    public InvalidReservationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
