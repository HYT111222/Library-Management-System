package com.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class Queue {
    @Id
    public int queueid;
    public String booksearchid;
    public String userid;
    public int queuestate;
}
