    package roomescape.Domain;

    public class Reservation {
        private long id;
        private String name;
        private String date;
        private String time;

        public Reservation() {}
        public Reservation(long id, String name, String date, String time)
        {
            this.id = id;
            this.name = name;
            this.date = date;
            this.time = time;
        }

        public Reservation(String name, String date, String time)
        {
            this.name = name;
            this.date = date;
            this.time = time;
        }

        public long getId() {
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

        public static Reservation toEntity(Reservation reservation, long id) {
            return new Reservation(id, reservation.name, reservation.date, reservation.time);
        }

        public boolean isValid() {
            return isNotNullOrEmpty(name) && isNotNullOrEmpty(date) && isNotNullOrEmpty(time);
        }

        private boolean isNotNullOrEmpty(String string) {
            return string != null && !string.isEmpty();
        }
    }
