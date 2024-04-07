package roomescape.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import roomescape.exception.NoArgsException;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// 현재는 데이터베이스가 없으니, 임의의 DTO 를 컨트롤러에 생성!

@Controller
public class ReservationController {

    public static List<Reservations> db = new ArrayList<>();
    public static class Reservations {
        public int id; // List 를 사용하여 저장할 때, index 와 List 의 Length 를 편하게 사용하기 위해, Long -> int 로 변경
        public String name;
        public String date;
        public String time;

        public Reservations(int id, String name, String date, String time) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.time = time;
        }
    }



    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservations>> reservations() {
        // 테스트에 맞게 3개를 생성하고 리턴

//        Reservations r1 = new Reservations(1L, "브라운", "2023-01-01", "10:00");
//        Reservations r2 = new Reservations(2L, "퍼플", "2023-01-02", "11:00");
//        Reservations r3 = new Reservations(3L, "그린", "2023-01-03", "12:00");
//
//        List<Reservations> response = new ArrayList<>();
//        response.add(r1);
//        response.add(r2);
//        response.add(r3);
        // 삼단계 미션으로 인한 변경.
        return new ResponseEntity<>(db, HttpStatus.OK);
    }


    @PostMapping("/reservations")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Reservations> reservations(@RequestBody Reservations request) throws Exception {
        /**
         * DB 가 없으니 일단, Controller 의 Static 변수에 저장하자.
         */


        /**
         * 필요한 값이 모두 받아졌는지 확인.
         */
        if (request.date.isEmpty() ||  request.name.isEmpty() || request.time.isEmpty()) {
            throw new NoArgsException();
        }

        // AtomicLong 을 사용하는 것보단,
        // DB 의 크기를 알고, 직접 가져온다면 더 좋지 않을까?
        int index = db.size();
        // Index 설정.
        request.id = index + 1;



        // DB 에 저장.
        db.add(request);
        return reservations(request.id);
    }
    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservations> reservations(@PathVariable int id) throws Exception {
        Reservations response;
        try {
            response = db.get(id - 1);

        } catch (Exception err) {
          throw new NotFoundReservationException();

        }
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Object> reservationDelete(@PathVariable int id) throws Exception {
        try {db.remove(id - 1);} catch (Exception err) {
            throw new NotFoundReservationException();
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Object> handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NoArgsException.class)
    public ResponseEntity<Object> handleException(NoArgsException e) {
        return ResponseEntity.badRequest().build();
    }
}
