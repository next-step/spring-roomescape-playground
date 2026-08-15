# 리뷰 피드백 체크리스트

- [x] 스프링 @Repository의 역할과 기능에 대한 학습 및 Repository 형식의 메서드 네이밍으로 변경
- [x] 테스트 네이밍을 실제 검증내용과 일치 및 검증내용 변경
- [x] 기능 명세서와 API 명세서의 차이를 학습해서 README에 반영
- [x] @Controller와 @RestController의 차이 학습 후 적용해보기
- [ ] 생성자 주입과 @Autowired 필드 주입 방식의 차이점 학습 (생성자가 하나일 때 자동 주입되는 방식 포함)
- [ ] 생성 시점에 객체의 유효성을 검증하도록 변경 (Reservation이 스스로 유효한 상태를 보장하도록)
- [ ] Stream 체이닝 가독성 향상을 위한 줄바꿈 정리
- [ ] DTO와 도메인 객체의 차이 학습 후, validate()/toEntity() 같은 변환 책임 분리
- [ ] ReservationTest를 통한 도메인 객체 검증 로직 테스트 코드 작성
- [ ] 예외 응답 본문을 만들어 void 대신 사용, 클라이언트가 실패 원인을 파악할 수 있도록 변경
- [ ] @RequestMapping 학습 후 적용 가능한 부분 적용
- [ ] @ControllerAdvice와 @ExceptionHandler의 역할을 정리해 리뷰 코멘트로 작성
- [ ] API 명세서에 예외 상황(400/404 등) 포함, 표 형태로 재정리 