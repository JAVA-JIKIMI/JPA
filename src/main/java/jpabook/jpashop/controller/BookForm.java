package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BookForm {

    @NotEmpty(message = "비어있음 안댐")
    private String id;

    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}
