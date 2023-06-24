package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

//@Mapper
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE userid = #{id}")
    User selectById(String id);

    @Update("UPDATE user SET password = #{password} WHERE userid = #{id}")
    boolean updatePassword(String password,String id);

}
