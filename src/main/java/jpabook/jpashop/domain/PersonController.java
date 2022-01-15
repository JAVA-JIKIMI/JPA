package jpabook.jpashop.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {
	@Autowired
	private PersonRepository personRepository;
	
	@PostMapping("{name}")
	public void save(@PathVariable String name) {
		Person person = Person.builder().name(name).build();
		personRepository.save(person);
	}
	
	@GetMapping
	public List<Person> get() {
		return personRepository.findAll();
	}
}
