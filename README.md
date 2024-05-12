## 1단계 홈 화면
- [x] 타임리프 모듈을 추가한다.

## 2단계 예약 조회
- [x] `/reservation GET` 요청 시, 예약 페이지(템플릿 파일)를 반환한다.
- [x] `/reservations GET` 요청 시, 예약 목록을 조회하는 API를 추가한다.
  - DB(`ArrayList`)에 저장된 예약자 명단을 `JSON` 타입으로 반환한다.
  - DTO 리스트를 반환한다. (`@ResponseBody`)

## 3단계 예약 추가 / 취소
- [x] `/reservations POST` 요청 시, 입력받은 JSON을 바탕으로 DB에 저장한다.
  - 201 반환
  - `Location` 응답 헤더에 `/reservations/{newId}` 추가하기
- [x] `/reservations/{1} DELETE` 요청 시, 입력받은 ID에 해당하는 객체를 DB에서 삭제한다.
  - 204 반환

## 4단계 예외 처리
- [x] 사용자가 잘못된 API 요청을 할 경우, 400 에러를 반환한다.
  - 예약 추가 시, 필수 인자 값이 부족할 경우
    - 날짜 누락
    - 이름 누락
    - 시간 누락
  - 예약 삭제 시, DB에 없는 ID 값을 요청할 경우
  - (필요한 경우, 더 만들어보기)
- [x] 400을 반환하도록 하는 커스텀 예외를 생성한다.

- [ ] `@ControllerAdvice` 학습하기 
