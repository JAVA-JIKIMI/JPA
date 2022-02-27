package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonServiceTest {
	@Autowired
	PersonService personService;

	@Test
	void updateName() {
		// given
		int id = 12;
		String address = "광주시";
		
		// when
		personService.updateName(id, address);
		
		// then
	}
}
