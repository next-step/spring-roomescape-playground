package roomescape.global.controller;

import org.springframework.http.ResponseEntity;
import roomescape.global.GlobalErrors;

public class InternalErrorException extends ApiException implements ExceptionWithErrorCode {
    private final String errorCode;

    public InternalErrorException(Throwable cause) {
        super(null, cause);
        errorCode = GlobalErrors.generateErrorCode();
    }


    @Override
    public String getMessage() {
        return "내부 오류가 발생했습니다. 개발자에게 문의해주세요.";
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public ResponseEntity<Dto> getResponse() {
        GlobalErrors.reportError(this, getCause());

        return ResponseEntity.internalServerError().body(new Dto());
    }


    public class Dto extends ApiException.Dto {
        @Override
        public String getType() {
            return "InternalError";
        }

        public String getErrorCode() {
            return errorCode;
        }
    }
}
