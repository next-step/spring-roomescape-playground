package roomescape.domain.dto;

public class TimeResponseDto {

    public static class Create{

        private Long id;
        private String time;

        public Create(Long id, String time) {
            this.id = id;
            this.time = time;
        }

        public Long getId() {
            return id;
        }

        public String getTime() {
            return time;
        }
    }

    public static class Get{

        private Long id;
        private String time;

        public Get(Long id, String time) {
            this.id = id;
            this.time = time;
        }

        public Long getId() {
            return id;
        }

        public String getTime() {
            return time;
        }
    }
}
