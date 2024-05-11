package roomescape.domain.dto;

public class ReservationResponseDto {

    public static class Create{

        private Long id;
        private String name;
        private String date;
        private String time;

        public Create(Long id, String name, String date, String time) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.time = time;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }
    }

    public static class Get{

        private Long id;
        private String name;
        private String date;
        private String time;

        public Get(Long id, String name, String date, String time) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.time = time;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }
    }
}
