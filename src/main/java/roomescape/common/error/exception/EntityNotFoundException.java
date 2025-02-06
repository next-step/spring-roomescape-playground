package roomescape.common.error.exception;

import ch.qos.logback.core.status.ErrorStatus;
import roomescape.common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }

}
