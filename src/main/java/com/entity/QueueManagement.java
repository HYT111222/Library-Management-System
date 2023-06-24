package com.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

/**
 * @author hushuo
 */
@Getter
@Setter
public class QueueManagement {
    @Id
    public int queueid;
    public String booksearchid;
    public String userid;
    public int queuestate; // （0代表是第一位，1代表第二位、-1代表结束）
}
