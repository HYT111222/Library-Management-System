package com.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.entity.*;
import com.mapper.*;
import com.service.UserService;
import com.vo.R;
import com.vo.param.*;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Random;

import com.util.md5;


/**
 * FileName:  UserServiceImpl
 * Date: 2023/04/13
 */
@Service
@AllArgsConstructor
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private final UserMapper userMapper;

    /**
     *
     * @param loginParam 登录参数
     * @return 结果
     */
    @Override
    public R login(@NotNull LoginParam loginParam) {
        R r = new R();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",loginParam.getUserid());
        if (userMapper.exists(queryWrapper)) {
           User user = userMapper.selectOne(queryWrapper);
           String salt = user.getSalt();
           String afterMd5 = md5.code(loginParam.getPassword(),salt);
           if (Objects.equals(afterMd5, user.getPassword())) {
               String token =user.getToken(user);
               r.data("token",token);
               return r.ok();
           } else {
              return r.error("密码错误");
           }
        } else {
            return r.error("用户不存在");
        }
    }

    /**
     * 生成salt
     * @return 10位随机字符salt
     */
    private String randomSalt(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){//默认salt为5位
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();

    }

    /**
     * 登录
     * @param registerParam 注册参数
     * @return 结果
     */
    @Override
    public R register(@NotNull RegisterParam registerParam) {
        R r =  new R();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", registerParam.getUserid());
        if (!userMapper.exists(queryWrapper)) {
            User user = new User();
            String salt = randomSalt();
            String md5_password = md5.code(registerParam.getPassword(),salt);
            user.setPassword(md5_password);
            user.setSalt(salt);
            user.setUserid(registerParam.getUserid());
            userMapper.insert(user);
            return r.ok();
        } else {
            return r.error("用户已存在");
        }
    }




    @Override
    public User findUserById(String id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", id);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }


}