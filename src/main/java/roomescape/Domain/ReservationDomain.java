    package roomescape.Domain;

    import roomescape.Controller.ReservationController;

    public class ReservationDomain {
        private long id;
        private String name;
        private String date;
        private String time;

        public ReservationDomain() {}
        public ReservationDomain(long id, String name, String date, String time)
        {
            this.id = id;
            this.name = name;
            this.date = date;
            this.time = time;
        }

        public ReservationDomain(String name, String date, String time)
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

        public static ReservationDomain toEntity(ReservationDomain reservation, long id) {
            return new ReservationDomain(id, reservation.name, reservation.date, reservation.time);
        }

        public boolean isValid() {
            return isNotNullOrEmpty(name) && isNotNullOrEmpty(date) && isNotNullOrEmpty(time);
        }

        private boolean isNotNullOrEmpty(String string) {
            return string != null && !string.isEmpty();
        }
    }
