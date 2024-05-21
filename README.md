[나만의 운동루틴 리팩토링 시작]

-앱 설계 리팩토링
-> 앱 기능 추가 예정으로 인해 코드의 가독성을 높이기 위해 설계 구조를 리팩토링의 필요성 대두
1. 디자인 패턴 존재 X -> MVVM 패턴
2. 아키텍처 존재 X -> 클린 아키텍처 (presentation -> domain <- data : 구글에서 권장하는 아키텍처랑 불일치)
3. LiveData -> StateFlow && SharedFlow 

-기능 추가

2024-05-21
1. Excercise 로직 -> MVVM 패턴 적용
