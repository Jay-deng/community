package com.yzcs.community.Dao;

import com.yzcs.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

// mybatis提供的注解，等同于@repository的作用
// 测试时发现提示"Could not autowire. No beans of 'UserMapper' type found. " 解决办法可以添加注解
//@Repository
@Mapper
public interface UserMapper {
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);

}
