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
