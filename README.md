[나만의 운동루틴 리팩토링 시작]

-앱 설계 리팩토링
-> 앱 기능 추가 예정으로 인해 코드의 가독성을 높이기 위해 설계 구조를 리팩토링의 필요성 대두
1. 디자인 패턴 존재 X -> MVVM 패턴
2. 아키텍처 존재 X -> 클린 아키텍처 (presentation -> domain <- data : 구글에서 권장하는 아키텍처랑 불일치)
3. LiveData -> StateFlow && SharedFlow 

-기능 추가

2024-05-21
0. 1차 리팩토링 
1. 클린 아키텍처(presentation -> domain <- data : 구글 권장 아키텍처랑 불일치) 분리 완료 : 커스텀 운동루틴에 대해서.
2. 커스텀 운동 루틴 추가 로직 -> MVVM 패턴으로 변경 (LiveData + Observe + Adapter)
3. Hilt로 의존성 주입
4. 전체 운동 루틴 제거 로직 -> MVVM 패턴으로 변경 (LiveData + Observe + Adapter)
