package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        final Member member = memberRepository.findOne(memberId);
        final Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        final OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        final Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order); // CascadeType.ALL(신중하게 범위를 지정해야 함)

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        final Order order = orderRepository.findOne(orderId);
        order.cancel(); // jpa의 강점(데이터만 바꿔놓으면 됨)
    }

//    public List<Order> findOrders(OrderSearch orderSearch) {
//        orderRepository.findAll(orderSearch);
//    }

}
