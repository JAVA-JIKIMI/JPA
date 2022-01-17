package jpabook.jpashop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jpabook.jpashop.domain.Cat;
import jpabook.jpashop.domain.Person;
import jpabook.jpashop.domain.PersonRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// @Transactional
public class PersonTest {
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	EntityManager entityManager;
	
	@Test
	public void test() {
		Person person = new Person();
		Cat cat = new Cat("cat1");
		List<Cat> cats = new ArrayList<>();
		cats.add(cat);
		person.setCats(cats);
		personRepository.save(person);
		// entityManager.persist(person);
		// entityManager.flush();
		// entityManager.clear();
		
		// Cat cat1 = cats.get(0);
		// cat1.setName("cat2");
		// cats.add(new Cat("cat3"));
		// person.setCats(cats);
		// entityManager.persist(person);
		// entityManager.flush();
		//
		// System.out.println(person);
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
		List<Cat> cats = person.getCats();
		cats.add(new Cat("cat3"));
		person.setCats(cats);

		personRepository.save(person);
		System.out.println(person);
	}
	
}
