package roomescape;

public class Reservations {
        private Long id;
        private String name;
        private String date;
        private String time;


        public Reservations(String id, String name, String date, String time) {
                this.id = Long.parseLong(id);
                this.name = name;
                this.date = date;
                this.time = time;
        }

        public Reservations(Long id, String name, String date, String time) {
                this.id = id;
                this.name = name;
                this.date = date;
                this.time = time;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getDate() { return date; }
        public String getTime() { return time; }

        public void setId(Long id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setDate(String date) { this.date = date; }
        public void setTime(String time) { this.time = time; }

}
