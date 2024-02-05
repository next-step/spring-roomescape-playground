package roomescape.controller;

import org.springframework.http.HttpStatus;

public enum ResponseInfo {
    CREATED(HttpStatus.CREATED),
    NOT_FOUND(HttpStatus.NOT_FOUND),

    BAD_REQUEST(HttpStatus.BAD_REQUEST);

    private final HttpStatus status;

    ResponseInfo(HttpStatus status){
        this.status = status;
    }
    public HttpStatus getStatus(){
        return status;
    }
}
