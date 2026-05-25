# 단계별 설명

## 5단계
    1. spring-boot-starter-jdbc 와 h2 의존성 추가
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
