package jpabook.jpashop.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import jpabook.jpashop.domain.Person;
import jpabook.jpashop.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {
	private final PersonRepository personRepository;
	
	@Transactional
	public void updateName(int personId, String address){
		Person person = personRepository.findById(personId).get();
		person.setAddress(address);
		personRepository.save(person);
	}
}
