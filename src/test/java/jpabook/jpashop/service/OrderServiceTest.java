package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.OrderRepository;

// 강의에서는 해당 테스트 케이스를 보여주지만 실제로는 좋지 않은 테스트이다. 
// 단위 테스트는 디비의 디펜던시도 없고 스프링 관련 빈들도 사용하지 않는 메소드 로직만 검증하는게 단위 테스트이다. 
// 실무에서 그렇게 작성할수 있을까...?ㅜㅜ
@SpringBootTest
@Transactional
class OrderServiceTest {

	@Autowired
	EntityManager em;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ItemRepository itemRepository;

	@Test
	public void 상품주문() throws Exception {
		// given
		Member member = createMember();
		Book book = createBook("시골 JPA", 10000, 10);

		int orderCount = 2;

		// when
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

		// then
		Order getOrder = orderRepository.findOne(orderId);

		assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
		assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
		assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
		assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
	}

	private Book createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member = new Member();
		member.setName("회원 1");
		member.setAddress(new Address("서울", "강가", "123-123"));
		em.persist(member);
		return member;
	}

	@Test
	public void 상품주문_재고수량초과() throws Exception {
		// given
		Member member = createMember();
		Book book = createBook("시골 JPA", 10000, 10);
		int orderCount = 11;

		// when
		assertThrows(NotEnoughStockException.class, () -> {
			orderService.order(member.getId(), book.getId(), orderCount);
			Assertions.fail("재고 수량 부족 예외가 발생해야 한다.");
		});
	}

	@Test
	public void 주문취소() throws Exception {
		// given
		Member member = createMember();
		Book item = createBook("시골 JPA", 10000, 10);
		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

		// when
		orderService.cancelOrder(orderId);

		// then
		Order getOrder = orderRepository.findOne(orderId);
		Item getItem = itemRepository.findOne(item.getId()); // 강의에서는 한번더 아이템을 호출하는 로직이 없다..;;
		
		assertEquals("주문 취소시 상태는 CANCEL 이다. ", OrderStatus.CANCEL, getOrder.getStatus());
		assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다. ", 10, getItem.getStockQuantity());
	}

}
