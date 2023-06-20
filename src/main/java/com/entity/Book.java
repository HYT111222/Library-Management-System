package com.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class Book {
    @Id
    private String booksearchid;
    private String author;
    private String bookname;
    private String press;
    private String language;
    private String contentabstract;
    private float averagescore;
    private String regionname;
    private int pagenumber;
    private int booknumber;
    private int bookremainder;

}
