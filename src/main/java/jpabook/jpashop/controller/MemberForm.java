package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "비어있음 안댐")
    private String name;

    private String city;
    private String string;
    private String zipcode;
    private String street;
}
