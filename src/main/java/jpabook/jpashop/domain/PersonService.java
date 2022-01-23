package jpabook.jpashop.domain;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersonService {
	@Autowired
	private PersonRepository personRepository;
	
	public Person getId(Long id){
		return personRepository.getById(id);
	}
}
