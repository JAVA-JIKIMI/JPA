package jpabook.jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Getter
@Entity
public class Cat {
	@Id
	private int id;
	private String name;
	private int age;
	
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
}
