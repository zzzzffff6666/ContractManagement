package com.zhang.contract.service;

import com.zhang.contract.entity.User;
import com.zhang.contract.mapper.UserMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Resource
    public UserMapper userMapper;
    @Resource
    public RightService rightService;
    @Resource
    public RoleService roleService;

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User selectUser(Object type) {
        User user;
        if (type instanceof Integer) {
            int id = (int)type;
            user = userMapper.selectUserByID(id);
        } else {
            String name = (String)type;
            user = userMapper.selectUserByName(name);
        }
        return user;
    }

    public int insertUser(User params) {
        //能获取插入的id是因为UserMapper.xml的insert语句新增了useGeneratedKeys和keyProperty参数
        if (userMapper.insertUser(params) == 1) {
            return params.getId();
        } else {
            return -1;
        }
    }

    public int deleteUser(String name) {
        return userMapper.deleteUser(name);
    }

    public int updateUser(User params) {
        return userMapper.updateUser(params);
    }

    public List<Integer> getRight(String name) {
        List<String> roles = rightService.selectRight(name);
        List<Integer> fun = new ArrayList<>();
        for (String r : roles) {
            fun.addAll(roleService.selectRole(r).getFunctionList());
        }
        return fun;
    }
}
