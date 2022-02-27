package jpabook.jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Setter;

@Entity
@Setter
public class Person {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String address;
}
