package roomescape.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponseEntity {

    private int status;
    private String name;
    private String message;

    public CustomResponseEntity() {

    }

    public CustomResponseEntity(int status, String name, String message) {
        this.status = status;
        this.name = name;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseEntity<CustomResponseEntity> toResponseEntity(HttpStatus httpStatus, String message) {
        return ResponseEntity
            .status(httpStatus)
            .body(new CustomResponseEntity(httpStatus.value(), httpStatus.name(), message));
    }
}
