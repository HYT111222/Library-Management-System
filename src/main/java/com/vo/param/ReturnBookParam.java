package com.vo.param;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 登录body参数
 */
@Getter
@AllArgsConstructor
public class ReturnBookParam {
    private String bookid;
    private Integer score;
    private String evaluateText;
}
