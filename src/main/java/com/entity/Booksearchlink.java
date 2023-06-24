package com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@TableName("booksearchlink")
public class Booksearchlink {
    @Id
    private String bookid;
    private String booksearchid;
    private String bookstate;

}
