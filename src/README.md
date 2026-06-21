# 인사
안녕하세요 그리디 백앤드 4기 이채현입니다.

방탈출 8-10단계 진행하였습니다.

# 고민한 부분

ReservationRepository나 TimeRepository를 작성할때 sql을 어떻게 작성해야 할지 보면서

이해하는 식으로 공부하는데, 아직 sql 이 구체적으로 어떻게 작성되는지에 대한 감이 안 잡힙니다.
어떤 것을 위주로 보고 이해하면 좋을지에 대한 조언 부탁드립니다 ㅎㅎ



# 8-10단계에서 한것

service를 도입하여 기존 controller에서 service와 controller 모든 책임을 지고 있는 것을 분리해주었습니다.


time domain/dto/repository/service를 생성하고 그에 맞게 reservation도 수정하였습니다/
# 프로젝트 구조

````
src/main/java/roomescape
├──controller  
│  ├──ReservationController.java        #예약 관련 HTTP 요청을 받아 Service에 전달
│  ├──TimeController.java               #시간 관련 HTTP 요청을 받아 Service에 전달
│  └──ViewController.java               # 뷰 관련 HTTP  요청을 받아 Service에 ㅈ전달
├──domain
│  ├──Reservation.java                  #Reservation에 들어가야할 정보                               
│  └──Time.java                         #Time에 들어가야 할 정보
├──dto                               
│  ├──ReservationRequest.java           #예약을 받기 위한 dto                               
│  └──TimeRequest.java                  #시간을 받기 위한 dto
├──exception                                
│  ├──GlobalExceptionHandler            #공통 핸들러
│  ├──InvalidReservationException       #잘못된 Reservation 내용 처리
│  ├──NotFoundReservationException      #Reservation 내용을 찾을 수 없는 예외처리
│  └──UnableReservationTimeException    #불가능한 시간에 관한 예외처리  
├──repository                          
│  ├──ReservationRepository.java        #Reservation DB 저장,조회,수정,삭제 역할을 함
│  └──TimeRepository.java               #Time DB 저장,조회,수정,삭제 역할을 함
├──service                        
│  ├──ReservationService.java           #예약 관련 비즈니스 로직 처리
│  └──TimeService.java                  #시간 관련 비즈니스 로직 처리
└──RoomescapeApplication        
````
