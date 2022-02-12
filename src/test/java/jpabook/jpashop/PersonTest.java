package jpabook.jpashop;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jpabook.jpashop.domain.Cat;
import jpabook.jpashop.domain.Person;
import jpabook.jpashop.domain.PersonRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PersonTest {
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	EntityManager entityManager;
	
	@Test
	@Rollback(value = false)
	public void test() {
		// Person person = new Person("incheol");
		// person.setId(1L);
		Person person1 = personRepository.getById(1L);
		person1.setName("test1111");
		Person person2 = personRepository.save(person1);

		person1.setAddress("address 1111"); // 무시될것 같다?
		person2.setName("incheol 4444");
		
		assertEquals(person1, person2);
	}
	
	@Test
	// @Transactional
	public void test2() {
		// Person person1 = new Person("incheol");
		// Cat cat = new Cat("cat1");
		// List<Cat> cats1 = new ArrayList<>();
		// cats1.add(cat);
		// person1.setCats(cats1);
		// personRepository.save(person1);
		//
		Person person = personRepository.findById(1L).orElseThrow();
		// personRepository.save()
		List<Cat> cats = person.getCats();
		cats.add(new Cat("cat3"));
		person.setCats(cats);

		personRepository.save(person);
		System.out.println(person);
	}
	
}
