### Spring MVC

#### Step 1. 홈화면

- [x] 정적 페이지를 활용하는 방식으로 메인 페이지를 호스팅 하였습니다.
- [x] 동적 페이지(thymeleaf)를 활용하는 방식으로 메인 페이지를 호스팅 하였습니다.

#### Step 2. 예약 화면

- [x] Thymeleaf 를 활용하는 방식으로 예약 조회 페이지를 호스팅 하였습니다.
- [x] 이와 함께 예약 리스트를 반환하는 /reservations API 를 구현하였습니다.

#### Step 3. 예약 화면

- [x] POST API 를 구현하였습니다.
- [x] Path parameter 사용하는 GET API 를 구현하였습니다.
- [x] DELETE API 를 구현하였습니다.

#### Step 4. 예외 처리

- [x] 등록 시, 필요한 값이 존재하지 않는 경우 에러를 발생시키도록 하였습니다.
- [x] 삭제 시, 해당 인덱스의 값이 존재하지 않는 경우 에러를 발생시키도록 하였습니다.
- [x] 에러 발생 시, ExceptionHandler 를 통해 에러를 처리하도록 하였습니다.

#### Step 5

- [x] H2 Database 와 Application 을 연결하기 위한 Jdbc 의존성을 추가하였습니다.
- [x] Database 에 Customers 및 Reservations 테이블을 생성하는 코드를 추가하였습니다.

#### Step 6

- [x] Reservations 를 관리하기 위한 Repository Class 를 작성하였습니다.
- [x] jdbc Driver 를 위한 Mapper Class 를 작성하였습니다.
- [x] 기존 코드들을 H2 Database 를 사용하기 위한 코드로 변경하였습니다.

#### Step 7

- [x] 테스트 코드를 추가하였습니다.

#### Step 8

- [x] 요구사항에 맞게 Time Table 을 생성하였습니다.
- [x] Time 테이블을 관리하기 위한 DTO 및 Controller, Repo 를 구성하였습니다.

#### Step 9

- [x] 생성된 Time 테이블을 참조하기 위해 Reservations 테이블을 수정하였습니다.
- [x] 수정된 Reservations 에 맞게 기존 Entity , Controller 등을 변경하였습니다.

#### Step 10

- [x] 기존에 코드를 계층화 시키기 위해 Controller - Service - Repository 로 역활을 분리하였습니다.
- [x] 변경된 Table 에 맞게 테스트 코드를 수정하였습니다.
- [x] 코드의 형식(일관성)을 맞추도록 수정하였습니다.