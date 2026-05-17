package roomescape.global.dto;

import java.util.List;

public record ApiInputErrorResult(String type, String message, List<ApiFieldError> fields) {
}
