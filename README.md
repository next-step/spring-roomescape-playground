1단계 - 홈 화면
- 요구 사항 테스트 : 메인 화면 띄우기
- gradle 의존성 추가
  - `implementation 'org.springframework.boot:spring-boot-starter-web'`
    `implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'`

2단계 - 예약 조회
- Reservation 객체 생성
  - `id`, `name`, `date`, `time`로 구성되어 있다.
- ReservationController 구성
  - `/reservation`, `/reservations` 두 가지의 매핑 구현.

3단계 - 예약 추가 / 조회
- 예약 객체에 대한 CRUD 구성
- 문제의 API 명세 조건에 맞게 헤더 구성
  - `Location: /reservations/1`
  - `Content-Type: application/json`

4단계 - 예외 처리
- 예약 관련 API 호출 시 에러가 발생하는 경우 요청의 문제인 경우 StatusCode(400) 반환
  - 예약 추가 시 필요한 인자값이 비어있는 경우
  - 삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우

코드 리뷰 반영 (리팩터링)
- Reservation 객체의 name field 유효성 검사 추가
  - 이름 입력이 안되었을 때
  - 이름이 숫자로만 구성되어 있을 때
- 예약 삭제 예외처리 수정
  - IllegalArgumentException -> NotFoundException

  
5단계 - 데이터베이스 적용하기 
- build.gradle 파일을 이용하여 다음 두 의존성 추가 
  - `spring-boot-stater-jdbc` 
  - `h2` 
- 테이블 스키마 정의 
  - 데이터베이스 테이블 생성을 위해 schema.sql 파일을 생성하고 테이블을 생성하는 쿼리 작성 
- 데이터베이스 설정
  - h2 데이터베이스의 console 기능을 활성화 
    - datasource url을 다음과 같이 지정하세요. 
      - `spring.datasource.url=jdbc:h2:mem:test`
      - `spring.h2.console.enabled=true`

6단계 - 데이터 조회하기
- 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용하도록 수정
- 데이터베이스에 예약 하나 추가 후 예약 조회 API를 통해 조회한 예약 수와 데이터베이스 쿼리를 통해 조회한 예약 수가 같은지 비교하는 테스트
- 수정한 것ㄹ
  - DAO, DTO 분리
  - 인메모리와 JDBC의 DAO 분리
  - 테스트 단계 분리 진행
  - Reservation 객체에서 모든 유효성 검사 진행

7단계 - 데이터 추가/삭제하기
- 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정
- 기존에 사용하던 List 및 AtomicLong 을 제거
- 예약 관리 기능이 정상 동작하도록 기능을 완성
- 예약 번호 불러오기 활성화
  - KeyHolder를 활용해 id 받기

8단계 - 시간 관리 기능
- 방탈출 시간표를 선택하는 방식으로 수정 
- API 명세를 따라 시간 관리 API를 구현
  - 시간 추가
  - 시간 조회
  - 시간 삭제

9단계 - 기존 코드 수정
- 예약 페이지 파일 수정
  - templates/reservation.html 대신 templates/new-reservation.html 파일을 활용
- 기존에 구현한 예약 기능에서 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정
- 테이블 스키마 재정의  팡
  - 외래키 지정을 통해 reservation 테이블과 time 테이블의 관계를 설정
- 예약 클래스 수정 
  - 시간 타입을 String에서 Time 객체로 수정
- 예약 쿼리 수정 
  - 예약 추가 시 시간을 문자열(ex. "10:00") 형태로 입력하던 부분을 Time의 식별자(ex. 1)로 수정
  - 조회 시 Time 정보도 함께 조회하기 위해 아래와 같이 쿼리를 수정
  - `SELECT
    r.id as reservation_id,
    r.name,
    r.date,
    t.id as time_id,
    t.time as time_value
    FROM reservation as r inner join time as t on r.time_id = t.id`

10단계 - 계층화 리팩터
- 레이어드 아키텍처를 적용하여 레이어별 책임과 역할에 따라 클래스 분리
- 분리한 클래스는 매번 새로 생성하지 않고 스프링 빈으로 등록해서 사용
- `ReservationController` 에 있던 데이터베이스 관련 로직을 다른 클래스로 분리한 것을 확인하기 위한 테스트 
- `ReservationController`에 `JdbcTemplate` 필드가 사라진 것을 확인하여 로직 분리를 확인
- 레이어드 아키텍처 
  - 컨트롤러는 웹 요청/응답 책임만 가지도록 수정 
  - 디비 접근 책임은 DAO(Data Access Object)가 가지도록 위임 
  - 비즈니스 플로우 책임은 서비스가 가지도록 위임
  - 비즈니스 규칙 책임은 도메인이 가지도록 위임