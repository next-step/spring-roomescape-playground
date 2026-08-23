# 리뷰 피드백 체크리스트(1~4단계)

- [x] 스프링 @Repository의 역할과 기능에 대한 학습 및 Repository 형식의 메서드 네이밍으로 변경
- [x] 테스트 네이밍을 실제 검증내용과 일치 및 검증내용 변경
- [x] 기능 명세서와 API 명세서의 차이를 학습해서 README에 반영
- [x] @Controller와 @RestController의 차이 학습 후 적용해보기
- [x] 생성자 주입과 @Autowired 필드 주입 방식의 차이점 학습 (생성자가 하나일 때 자동 주입되는 방식 포함)
- [x] 생성 시점에 객체의 유효성을 검증하도록 변경 (Reservation이 스스로 유효한 상태를 보장하도록)
- [x] Stream 체이닝 가독성 향상을 위한 줄바꿈 정리
- [x] DTO와 도메인 객체의 차이 학습 후, validate()/toEntity() 같은 변환 책임 분리
- [x] ReservationTest를 통한 도메인 객체 검증 로직 테스트 코드 작성
- [x] 예외 응답 본문을 만들어 void 대신 사용, 클라이언트가 실패 원인을 파악할 수 있도록 변경
- [x] @RequestMapping 학습 후 적용 가능한 부분 적용
- [x] @ControllerAdvice와 @ExceptionHandler의 역할을 정리해 리뷰 코멘트로 작성
- [x] API 명세서에 예외 상황(400/404 등) 포함, 표 형태로 재정리 

# 리뷰 피드백 체크리스트 (5~7단계)

- [x] RestController의 불필요한 매핑 빈 문자열("") 제거 (@RequestMapping의 기본 경로를 그대로 사용하도록 생략)
- [x] Repository 메서드명을 데이터 접근 의도가 드러나게 변경 (readReservations -> findAll, reserve -> save)
- [x] KeyHolder.getKey()가 null일 수 있는 경우에 대한 검증 및 예외처리 추가
- [x] DELETE 응답의 반환 타입을 ResponseEntity<Void>로 변경해 본문 없음을 명시
- [x] JdbcTemplate 학습 정리: 순수 JDBC 대비 JdbcTemplate이 대신 처리해주는 작업(Connection/PreparedStatement/ResultSet/자원 해제) 정리 및 query/queryForObject/update 등 메서드별 적합 상황 정리
- [ ] Reservation 도메인 객체의 이름 검증 로직 수정
- [ ] Controller에서 ReservationRequest를 Reservation으로 변환 후 Repository에 전달, Repository가 DTO 대신 도메인 객체에 의존하도록 변경
- [ ] 조회 결과를 도메인 객체로 매핑하는 로직을 RowMapper로 분리
- [ ] JdbcTemplate 메서드 추가 학습 정리: query/queryForObject 결과 0건일 때의 동작 차이, 발생하는 예외 종류, RowMapper의 역할 정리
- [ ] Lombok 학습