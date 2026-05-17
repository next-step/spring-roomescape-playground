package roomescape.global;

import roomescape.global.controller.ExceptionWithErrorCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalErrors {
    private static final String INSTANCE_CODE = generateRandomInstanceCode();
    private static final AtomicInteger ERROR_CODE_INDEX = new AtomicInteger();

    private static String generateRandomInstanceCode() {
        byte[] code = new byte[3];
        new Random().nextBytes(code);
        return new String(Base64.getEncoder().encode(code));
    }

    /**
     * Example: {@code 2026-05-10:kF8M:ff8a}
     */
    public static String generateErrorCode() {
        StringBuilder code = new StringBuilder();
        LocalDate now = LocalDate.now();
        code.append(now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        code.append(":");
        code.append(INSTANCE_CODE);
        code.append(":");
        code.append(Integer.toUnsignedString(ERROR_CODE_INDEX.getAndIncrement(), 16));
        return code.toString();
    }

    // TODO: 적당한 로깅 시스템 사용, development 모드일 경우 클라이언트에 내부 오류도 같이 전송
    public static void reportError(ExceptionWithErrorCode source, Throwable cause) {
        System.out.println("[" + source.getErrorCode() + "] 오류 발생: " + source.getMessage());
        cause.printStackTrace();
    }
}
