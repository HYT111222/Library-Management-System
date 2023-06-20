package com.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class BookSearchLink {
    @Id
    private String bookid;
    private String booksearchid;
    private String bookstate;

}
