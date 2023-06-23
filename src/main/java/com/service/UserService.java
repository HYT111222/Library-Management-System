package com.service;

import com.annotation.UserLoginToken;
import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.User;
import com.vo.R;
import com.vo.param.*;



public interface UserService extends IService<User> {

    R login(LoginParam loginParam);

    R register(RegisterParam registerParam);

    R modifyPassword(String newpassword,String id);

    User findUserById(String id);

}
