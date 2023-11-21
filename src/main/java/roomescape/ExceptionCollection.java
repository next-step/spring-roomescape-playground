package roomescape;

class NotFoundReservationException extends RuntimeException{
    public NotFoundReservationException(){}
    public NotFoundReservationException(String message){super(message);}
}
