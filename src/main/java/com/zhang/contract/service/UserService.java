package com.zhang.contract.service;

import com.zhang.contract.entity.User;
import com.zhang.contract.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    public UserMapper userMapper;

    public boolean Login(String name, String password) {
        logger.info("开始查询数据");
        User user = userMapper.selectUserByName(name);
        if (user == null) return false;
        return user.getPassword().equals(password);
    }

    public List<User> selectAll() {
        logger.info("开始查询数据");
        return userMapper.selectAll();
    }

    public User selectUser(Object type) {
        logger.info("开始查询数据");
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
        logger.info("开始提交数据");
        int result = userMapper.insertUser(params);
        //能获取插入的id是因为UserMapper.xml的insert语句新增了useGeneratedKeys和keyProperty参数
        Integer insertId = params.getId();
        System.out.println("插入数据的ID: " + insertId);
        System.out.println("insert结果: " + result);
        if (result == 1) {
            return insertId;
        } else {
            return 0;
        }
    }

    public int deleteUser(String name) {
        logger.info("开始删除数据");
        int result = userMapper.deleteUser(name);
        System.out.println("删除数据结果: " + result);
        // insert返回结果为 1，表示插入了一条数据
        return result;
    }

    public int updateUser(User params) {
        logger.info("开始修改数据");
        int result = userMapper.updateUser(params);
        System.out.println("修改数据结果: " + result);
        return result;
    }
}
