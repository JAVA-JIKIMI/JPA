## Q. 엔티티는 setter 메소드 없이 어떻게 값을 바인딩 하는가?

A. 하이버네이트는 엔티티를 빈 생성자로 생성하여 reflection 기능을 통해서 값을 바인딩 하기 때문에 setter를 사용하지 않아도 된다. 그렇기 때문에 엔티티에 별도의 생성자를 생성할 경우, 기본 생성자인 빈 생성자를 사용할수 없으므로 에러가 발생한다. 그래서 엔티티에 커스텀 생성자를 사용할때는 빈 생성자를 추가로 선언해주어야 한다.

### Q. 그렇다면 값을 주입하는 방법은 필드 주입 밖에 없는가?

A. 그렇지 않다. 메소드 기반으로 주입할 수 있는 방법도 있다. 엔티티의 값 주입 방법을 결정하는 요인은 @Id를 어디에 선언하는가 따라 결정된다. 만약 필드 상단에 @Id를 선언할 경우, 필드 주입방식을 사용하게 되고, getter 메서드에 선언하게 되면 메서드 주입 방식을 사용하게 된다. 또는 주입 방식을 명시적으로 선택할 수 있다. (@Access 사용하면 된다.)

```jsx
@Entity
@Getter
@Access(AccessType.PROPERTY) // AccessType.PROPERTY, AccessType.FIELD
public class Person {

    private Long id;

    @Id
    @GeneratedValue
    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
    this.id = id;
}
```

### Q.  엔티티에 사용된 필드는 무조건 데이터베이스에 반영되는가?

A. 기본적으로는 테이블 생성시 엔티티 클래스에 필드는 스키마에 반영되지만 만약 스키마에 반영하고 싶지 않다면 @Transient를 사용하여 스키마에 반영하지 않을 수도 있다.

```jsx
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Transient
    private int age;
}
```

age 필드에 @Transient를 설정하면 ddl 시, 해당 필드는 반영하지 않는다.

![1111](https://user-images.githubusercontent.com/2491418/149650699-10df3181-8ce5-4443-b88f-2b0bb5ce224e.png)


### 참고 자료

- [https://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/chapters/domain/access.html](https://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/chapters/domain/access.html)

## Q. 양방향 관계에서 JoinColumn은 어디에 선언해야 하는가?

A. 다대일 관계 또는 일대일이면 아무곳이나 선언해도 상관없다. 그리고 양방향 연관관계에서는 JoinColumn만 선언할 경우 매핑 테이블이 자동으로 생성된다. 그러므로 매핑 테이블을 생성하지 않기 위해서는 JoinColumn을 걸어주지 않은 반대편 Entity에는 “mappedBy” 옵션을 추가해주어야 한다.

### Q. JoinColumn을 사용하면 FK로 선언되는데 FK는 꼭 필요한가?

![2222](https://user-images.githubusercontent.com/2491418/149650703-3996d827-baff-49a4-8135-46b01c60a758.png)


A. JoinColumn을 선언하면 테이블 생성시 자동으로 FK로 선언이 되는건 맞다. 하지만 FK로 선언하는게 꼭 필요하다고 생각하는건 아니다. 성능이 더 중요한 로직이라면 FK 조건을 안 걸어도 된다고 생각한다. FK 조건은 성능보다는 비즈니스 로직에 더 중점을 둔다고 한다면 FK 조건이 필요할 수도 있을것 같다. 그러나 그건 일반적인 케이스이고 현재는 케이스 바이 디비이겠지만 mysql이나 postgres SQL등 대부분의 케이스에서 FK 생성시 자동으로 인덱스도 생성되는것을 알수 있다. (이번에 안 사실인데 H2 DB도 인덱스가 생성된다..)


![3333](https://user-images.githubusercontent.com/2491418/149650707-cf593686-27d7-49a6-afc5-c256d7ff6d8e.png)


### 참고

- [https://www.dataversity.net/foreign-keys-and-the-delete-performance-issue/](https://www.dataversity.net/foreign-keys-and-the-delete-performance-issue/)
- [https://stackoverflow.com/questions/836167/does-a-foreign-key-automatically-create-an-index/836176](https://stackoverflow.com/questions/836167/does-a-foreign-key-automatically-create-an-index/836176)

## Q.  Entity에서 collection 필드 사용시 선언 단계에서 초기화 하는 이유는?


![4444](https://user-images.githubusercontent.com/2491418/149650713-fc21ee50-4497-4e8c-ab5a-dcd6f627357e.png)


A. 하이버네이트는 엔티티가 영속 상태로 전환하게 되면 내부 컬렉션 객체를 영속성 컨텍스트에서 관리할수 있는 타입으로 변환하게 된다. (PersistentBag)
하이버네이트 도큐먼트 설명에는 정렬되지 않고 키가 없는 컬렉션이라고 설명하였고, 개발자들이 생각하기에는 컬렉션이라고 생각하면 된다고 나와있다.


![5555](https://user-images.githubusercontent.com/2491418/149650715-76253649-79f1-4b5f-a83f-504a19ffbebb.png)


그러므로 하이버네이트에서 해당 컬렉션을 관리할 수 있는 타입으로 변환하기 때문에 선언 즉시 Collection을 초기화하는게 영속성 컨텍스트에서 관리할수 있는 객체로 유지하기 위한 적절한 해결방안이라고 할 수 있다.

### 참고

- [https://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/chapters/domain/access.html](https://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/chapters/domain/access.html)

## Q. 편의 메소드는 무엇이고, 왜 필요한가?

A. 양방향 연관관계일 경우, 둘중 하나의 엔티티는 주인이 되어 데이터베이스와 연동되도록 설정해주어야 한다. 해당 기능을 수행할 수 있는게 “mappedBy”의 역할이다. “mappedBy”로 선언된 엔티티는 영속성 컨텍스트에 저장되어 커밋될 경우, 데이터베이스에도 반영이 된다. 그러나 mappedBy의 역할은 데이터베이스에 연동하기 위한 주인을 결정할 뿐이다. 실제로 우리가 로직을 구현함에 있어서는 논리적으로 양방향 연관관계에 있는 양쪽 객체에 데이터가 존재해야 한다. 그렇기 위해서는 편의 메소드를 정의해야 할 필요가 있다.

```jsx
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // order와 member의 연관관계를 유지하기 위한 편의 메소드
    public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
}

// order와 orderItem의 연관관계를 유지하기 위한 편의 메소드
public void addOrderItem(OrderItem orderItem) {
    this.orderItems.add(orderItem);
    orderItem.setOrder(this);
}

// order와 delivery의 연관관계를 유지하기 위한 편의 메소드
public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
}
}
```

### 참고

- [https://www.youtube.com/watch?v=brE0tYOV9jQ&t=1s](https://www.youtube.com/watch?v=brE0tYOV9jQ&t=1s)
