## 단계별 설명

### 1단계

localhost:8080 요청시, 어드민 메인 페이지가 응답할 수 있어야 함,

### 2단계

/reservation 창을 보여줄 수 있어야 함.

### 3단계

/reservations 를 통해 삭제와 추가 구현

### 4단계

- 예약 추가시 필요한 인자값이 비어있는 경우 400에러 출력
- 삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우

위 두가지에 대한 오류를 400으로 구현

## 프로젝트 구조

src/main/java/roomescape

```text
├──controller  
│  ├──ReservationController.java        # 예약 화면 반환 컨트롤러  
│  └──HomeController.java               # 홈 화면 반환 컨트롤러  
├──domain                               
│  └──Reservation                       # 예약 저장을 위한 domain
├──exception                                
│  ├──GlobalExceptionHandler            #공통 예외 핸들러
│  ├──InvalidReservationException       #예약추가시-인자값이 비어있는경우 예외 case
│  └──NotFoundReservationException      #예약취소시- 예약을 찾을 수 없는경우 예외 case
└──RoomescapeApplication        
'''

