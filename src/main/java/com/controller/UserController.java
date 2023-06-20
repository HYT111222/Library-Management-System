package com.controller;

import com.annotation.PassToken;
import com.annotation.UserLoginToken;
import com.service.UserService;
import com.vo.R;
import com.vo.param.*;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

/**
 * FileName:  UserController
 * Date: 2023/04/01
 */

@RestController
@RequestMapping("/user")
//@CrossOrigin
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    // 登陆--post保存
    @PostMapping("login")
    @PassToken
    public R login(@RequestBody LoginParam loginParam) {
        return userService.login(loginParam);
    }

    // 注册--post保存
    @PostMapping("register")
    @PassToken
    public R register(@RequestBody RegisterParam registerParam) {
        return userService.register(registerParam);
    }

/**
 * 以下为鉴权和使用发送请求的用户id示例
 * @UserLoginToken：需要鉴权
 * @RequestAttribute(value="id") String id ：用于获取用户id，其中id的命名可变
 * @PassToken：不需要鉴权
 */
    @GetMapping("managerHomePage")
    @UserLoginToken
    public R managerHomePage(@RequestAttribute(value="id") String id)
    {
        System.out.println(id);
        R r= new R();
        return r.ok();
    }
}
