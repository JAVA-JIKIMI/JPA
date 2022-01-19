## JPA 사용 이유

| **Supports:**                        | **Serialization** | **JDBC** | **ORM** | **ODB** | **EJB 2** | **JDO** | **JPA** |
| ------------------------------------ | ----------------- | -------- | ------- | ------- | --------- | ------- | ------- |
| Java Objects                         | **Yes**           | No       | **Yes** | **Yes** | **Yes**   | **Yes** | **Yes** |
| Advanced OO Concepts                 | **Yes**           | No       | **Yes** | **Yes** | No        | **Yes** | **Yes** |
| Transactional Integrity              | No                | **Yes**  | **Yes** | **Yes** | **Yes**   | **Yes** | **Yes** |
| Concurrency                          | No                | **Yes**  | **Yes** | **Yes** | **Yes**   | **Yes** | **Yes** |
| Large Data Sets                      | No                | **Yes**  | **Yes** | **Yes** | **Yes**   | **Yes** | **Yes** |
| Existing Schema                      | No                | **Yes**  | **Yes** | No      | **Yes**   | **Yes** | **Yes** |
| Relational and Non-Relational Stores | No                | No       | No      | No      | **Yes**   | **Yes** | No      |
| Queries                              | No                | **Yes**  | **Yes** | **Yes** | **Yes**   | **Yes** | **Yes** |
| Strict Standards / Portability       | **Yes**           | No       | No      | No      | **Yes**   | **Yes** | **Yes** |
| Simplicity                           | **Yes**           | **Yes**  | **Yes** | **Yes** | No        | **Yes** | **Yes** |



https://docs.oracle.com/cd/E21764_01/apirefs.1111/e13946/ejb3_overview_why.html

### 직렬화 "안되는게 많았다"

- 사용하기는 쉽지만, 대용량 데이터를 처리하는데 적합하지 않음
- 트랜잭션 무결성을 보장 X  
- 동시성 보장 X
- 쿼리 기능 제공 X

-> 아주 사소한 DB 요청할 때 빼고는 쓸모가 없다.

### JDBC "객체와 관계 사이 매핑이 안된다"

- 직렬화의 대부분의 단점을 극복.

- 대용량 데이터 처리 가능하고, 무결성을 보장하는 메커니즘이 있고, 정보에 대한 동시 액세스를 지원하고, SQL로 된(RDBMS 데이터 관리용 프로그래밍 언어) 정교한 쿼리 언어가 있음.

- 그러나 사용 용이성은 떨어짐.

- JDBC의 관계형 패러다임은 객체를 저장하도록 설계되지 않음
    - 영속성 데이터를 처리할 때 객체 지향을 포기하고 코드를 짜거나 or 객체 지향 개념을 관계형 DB와 매핑할 방법을 개인이 고려해야 한다. ( = 객체 관계 매핑 )

### ORM 프레임워크 "벤더사에 종속된다"

Object-Relational Mapping framework.

- 객체 관계 매핑에 대해 고민할 필요가 없다. 

- 그러나 단일 벤더사에만 존재하는 고유 API가 존재해서 개발시에 코드를 해당 인터페이스에 맞게 짜게 되고, 
추후 벤더사가 버그픽스를 해주지 않거나, 새 피쳐 개발을 하지 않거나 하면 다른 벤더 제품을 써야 하는데 
그 때 영속성 관련 코드를 새로 짜야 하는 문제가 있다. 
    - 이를 벤더 종속(vender lock-in) 이라고 한다.

### ODB "아예 객체형 DB를 만들자"

- 객체 관계 매핑할 필요가 없도록 객체를 저장하도록 설계된 DB 형식

- 객체-관계 매핑 소프트웨어보다 사용하기는 쉽다.

- ODMG라는 객체 DB에 액세스하기 위한 표준 API도 만들었다.

- 그러나 ODMG를 지키는 공급업체는 거의 없어서 여기서도 벤더 종속이 발생한다.

- 기업에서도 검증된 RDB에서 알려지지 않은 ODB로의 전환을 꺼려하고, 데이터 분석 도구도 적다. 이미 방대한 데이터가 RDB에 저장되어있어 큰 성과는 거두지 못했다.

### EJB 2 "객체 관계 매핑이 제한적으로 구현됐다"

- Enterprise Java Bean (spring과 비교되는 그 EJB가 맞다.)

- 2.x 버전에서 객체 관계 매핑을 지원. 엄격한 표준이 존재해 벤더간 이식 가능.

- 그러나 상속, 다형성과 같은 고급 기능은 지원하지 않는다.

- EJB가 늘 스프링과 비교당하는 내용으로, 코드 짜기가 어렵고 서버를 실행시는게 종종 무겁다.

### JDO "다양한 DB형태를 지원한다"

- JPA와 유사한 API를 사용한다.

- 그러나 RDBMS만 지원하는 JPA와 달리 JDO는 RDBMS도 지원하지만, 비관계형 DB도 지원한다.
  이게 사양을 희석한다는 말도 있다.

- apache JDO 재단의 JDO vs JPA 비교  
  (보통 1등은 이런 글을 쓰지 않는데..?)  
  https://db.apache.org/jdo/jdo_v_jpa.html

### JPA "영속성 메커니즘 최고의 기능"

- JDBC가 지원하는 대용량 데이터 set, 데이터 일관성, 동시성, 쿼리 기능을 지원한다.

- 객체-관계 매핑을 지원하며 상속과 같은 고급 객체 지향 개념도 사용 가능하다.

- 엄격한 사양을 지니고 있어 벤더 종속을 방지한다.

- RDBMS 에 중점을 둔다.

- JDO와 마찬가지로 사용하기 쉽다.


### JDO vs JPA (구글 트렌드 분석)
https://trends.google.co.kr/trends/explore?q=JDO,JPA
