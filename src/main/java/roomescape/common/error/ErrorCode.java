package roomescape.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    ;

    private final int status;
    private final String description;
    private final String code;


}
