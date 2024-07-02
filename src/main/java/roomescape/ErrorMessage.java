package roomescape;

public enum ErrorMessage {
    NOT_INPUT_FORMAT("입력 형식이 올바르지 않습니다"),
    NOT_FOUND_RESERVATION("해당 예약을 찾을 수 없습니다");

    String message;
    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}

