package roomescape.domain;

import jakarta.validation.constraints.NotBlank;

public class Reservation {
    private long id;

    @NotBlank(message="Name cannot be empty")
    private String name;

    @NotBlank(message="Date cannot be empty")
    private String date;

    @NotBlank(message="Time cannot be empty")
    private String time;

    public Reservation(){}

    public long getId() {return this.id;}
    public String getName() {return this.name;}
    public String getDate() {return this.date;}
    public String getTime() {return this.time;}

    public long setId(long id) {return this.id = id;}
    public String setName(String name) {return this.name = name;}
    public String setDate(String date) {return this.date = date;}
    public String setTime(String time) {return this.time = time;}
}
