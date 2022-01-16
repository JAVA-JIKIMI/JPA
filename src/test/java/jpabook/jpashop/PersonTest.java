package jpabook.jpashop;

import java.util.Collections;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jpabook.jpashop.domain.Cat;
import jpabook.jpashop.domain.Person;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PersonTest {
	@Autowired
	EntityManager entityManager;
	
	@Test
	public void test() {
		Person person = new Person();
		person.setCats(Collections.singletonList(new Cat()));
		
		entityManager.persist(person);

		System.out.println(person);
	}
	
}
