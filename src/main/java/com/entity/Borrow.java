package com.entity;

import lombok.Getter;
import lombok.Setter;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Setter
public class Borrow {
    @Id
    private String userid;
    private String bookid;
    private String loantime;
//    private Timestamp loantime;
//    private Timestamp returntime;
    private String returntime;
    private int score;
    private String evaluatetext;
}
