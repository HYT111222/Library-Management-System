package com.vo.param;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 登录body参数
 */
@Getter
@AllArgsConstructor
public class LoginParam {

    private String password;

    private String userid;


}
