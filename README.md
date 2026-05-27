# 인사
안녕하세요 그리디 백앤드 4기 이채현입니다.

# 고민지점

DB라는 새로운 개념이 어떻게 작동하는 것인지 공부해보면서는 조금 알게 되었는데,
아직 DB에 대해서 완전히 잘 알지 못하는 것 같습니다.

또한, DB라는 개념을 새로 도입시켜서, 기존 spring에 연결할때, controller에서 DB를 어떻게 
주고 받아야 하는지에 대해 많은 고민이 있었습니다.


# 단계별 설명

## 5단계
    1. spgring-boot-starter-jdbc 와 h2 의존성 추가
    2. 테이블 스키마 정의
    3. 데이터 베이스 설정
## 6단계
    SQL-select 쿼리를 이용하여 데이터 베이스 테이블 접근
## 7단계
    SQL- insert/delete 쿼리를 이용하여 데이터 베이스 테이블 접근
# 프로젝트 구조

````
src/main/java/roomescape
├──controller  
│  ├──ReservationController.java        # 예약 화면 반환 컨트롤러  
│  ├──ReservationRepository.java        # 예약 데이터 DB
│  └──ViewController.java               # 화면 반환 컨트롤러  
├──domain                               
│  └──Reservation                       # 예약 저장을 위한 domain
├──dto                               
│  └──ReservationRequest                # 예약을 받기 위한 dto
├──exception                                
│  ├──GlobalExceptionHandler            #공통 예외 핸들러
│  ├──InvalidReservationException       #예약추가시-인자값이 비어있는경우 예외 case
│  └──NotFoundReservationException      #예약취소시- 예약을 찾을 수 없는경우 예외 case
└──RoomescapeApplication        
````
