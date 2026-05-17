package roomescape.reservation.domain;

import roomescape.global.domain.DomainException;

public abstract class ReservationException extends DomainException {
    public ReservationException(String message) {
        super(message);
    }


    public static class InputFormat extends ReservationException {
        private final String field;

        public InputFormat(String field, String message) {
            super(message);
            this.field = field;
        }

        public String getField() {
            return field;
        }


        public static class NameTooLong extends InputFormat {
            public NameTooLong(int maxLength) {
                super("name", "예약자 이름이 최대 길이를 초과했습니다. 최대 " + maxLength + "자여야 합니다.");
            }
        }


        public static class IllegalName extends InputFormat {
            public IllegalName() {
                super("name", "이름에는 숫자, 기호, 띄어쓰기 등을 제외한 글자만 입력할 수 있습니다.");
            }
        }
    }


    public static class DuplicateTime extends ReservationException {
        public DuplicateTime() {
            super("해당 시간에 다른 예약이 이미 존재합니다.");
        }
    }


    public static class DoesNotExist extends ReservationException {
        public DoesNotExist() {
            super("해당 예약이 존재하지 않습니다.");
        }
    }
}
