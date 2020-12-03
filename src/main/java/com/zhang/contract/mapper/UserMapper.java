package com.zhang.contract.mapper;

import com.zhang.contract.entity.User;

import java.util.List;

public interface UserMapper {

    public int insertUser(User params);

    public User selectUserByID(Integer id);

    public User selectUserByName(String name);

    public List<User> selectAll();

    public int updateUser(User params);

    public int deleteUser(String name);
}
