package roomescape.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    ;

    private final int status;
    private final String code;
    private final String description;


}
