## 1단계 홈 화면
- [x] 타임리프 모듈을 추가한다.

## 2단계 예약 조회
- [x] `/reservation GET` 요청 시, 예약 페이지(템플릿 파일)를 반환한다.
- [x] `/reservations GET` 요청 시, 예약 목록을 조회하는 API를 추가한다.
  - DB(`ArrayList`)에 저장된 예약자 명단을 `JSON` 타입으로 반환한다.
  - DTO 리스트를 반환한다. (`@ResponseBody`)
