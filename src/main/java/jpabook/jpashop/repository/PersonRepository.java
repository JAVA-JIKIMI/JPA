package jpabook.jpashop.repository;

import org.springframework.data.repository.CrudRepository;

import jpabook.jpashop.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
