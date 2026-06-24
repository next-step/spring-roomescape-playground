package roomescape.domain;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time; //이제 문자열이 아니라 Time 도메인 객체를 가짐
    //id와 실제 시간 값을 둘다 가진 time 객체 전체를 뜻함

    public Reservation() {}

    public Reservation(Long id, String name, LocalDate date, Time time){
        this.id=id;
        this.name=name;
        this.date=date;
        this.time=time;
    }

    //getter 메서드들
    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public LocalDate getDate(){
        return date;
    }

    public Time getTime(){
        return time;
    }
}


