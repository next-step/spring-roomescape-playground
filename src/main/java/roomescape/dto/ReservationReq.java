package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;

@Getter
@AllArgsConstructor //기본 생성자 자동 생성
public class ReservationReq {
    //캡슐화를 위해서는 private으로 해야 하지만, step12의 코드에서 private으로 하면 JSON이 접근을 못 해서 직렬화 못 하는 에러 발생
    //해결을 위해 lombok의 getter 추가
    private Long id;
    private String name;
    private String date; //우선 String으로.. 차후 Date로 형변환 필요하면 교체..
    private Time time; //동일!!


}
