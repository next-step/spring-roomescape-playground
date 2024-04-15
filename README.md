
일단 여기에다가 나눌 부분 적겠습니다
controller vs requestioncontroller 에 차이점에 대해서 좀 더 공부해야할 것 같습니다 
controller로 json 파일 불러오려면 responsebody를 가져와야하고 
requestioncontroller 를 사용할 경우 return url 에서 html로 가지 않고 글이 적히는데 왜 그런지? 궁금해서 좀 더 찾아보고자 합니다

그리고 이와 관련해서 고민한 부분이 
get방식에서는 화면 불러오기만하고 (만들어진 html파일을)
수정된 데이터도 CURD를 하려면 post방식을 써야하는데 생성, 수정, 삭제는 reservations/{id}로 url구분이 되는데 읽어오기는 같은 url을 사용하면 안돼서 
get방식은 reservation
post방식은 reservations로 해서 이 데이터들을 reservation 에 연결된 html에 전달하는 식으로 진행했는데 다른 분들은 어떻게 하셨는지? 같은 url을 사용하면 안되는 건지? 좀 더 알아보고자 합니다
사실 단순하게 생각하면 같은 화면에서 일어나는 일이니까 url을 똑같이 쓰는게 좋다고 생각했습니다.

