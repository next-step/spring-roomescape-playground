package roomescape.global.response;

public record ErrorResponse(
        String code,
        String message
) {
}
