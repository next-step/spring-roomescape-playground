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
- [ ] `/reservations/{1} DELETE` 요청 시, 입력받은 ID에 해당하는 객체를 DB에서 삭제한다.
  - 204 반환
