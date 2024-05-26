*** 1단계 화면 생성 ***

 controller, model, exception 패키지 생성
 
[gradle 첨가]
implementation 'org.springframework.boot:spring-boot-starter-web' 
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'


**** 2단계 화면 조회 ****

1. exception 패키지에 예외 처리 record (Date, Id, Name, Time) 생성
2. model 패키지에 Reservation 객체 그햔
3. controller 패키지에 ReservationController 만든 후 페이지 요청(/reservation) 및 목록 조회(/reservations) 구현

*****3단계 정보 생성 및 삭제 *****

1. 생성관련 메소드는 PostMapping을 이용할 것.
2. 삭제관련 메소드는 DeleteMapping을 이용할 것.

 ****4단계 예외 처리 ******
전역적으로 다루는 예외 함수를 이용하여 특정 원하는 오류값을 리턴할 계획

   
 ****5단계 데이터베이스 적용하기 ******

 gradle , h2 , 스키마 파일 넣기

  ****6단계 데이터베이스 조회하기 ******
1. Getmapping(/reservations) 를 했을 때 해당 데이터 베이스에 있는 자료들이 화면에 보여야 된다.
   그러면 해당 함수에 데이터 베이스 전체를 불러오는 List를 선언해서 리턴해줘야 겠다.

2. 데이터 베이스를 관리할 entity를 새로 만들자 db 패키지 안에 ReservationEntity
3. 직접적으로 db와 소통을 하기 위한 객체가 필요하다. Service 패키지 안에 Update 및 Query 과 관련된 객체 생성
4. 해당 객체의 맞게 함수 만들기.

  ****7단계 데이터베이스 적용하기 ******
  위에 구현해 놨던 함수에 delete 및 insert와 해당 id를 건내줄 경우 해당 튜플을 반환해주는 함수를 구현해보자.
