package com.zhang.contract.service;

import com.zhang.contract.entity.Role;
import com.zhang.contract.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private RoleMapper roleMapper;

    public List<Role> selectAll() {
        logger.info("开始查询数据");
        return roleMapper.selectAll();
    }

    public String selectRole(Object type) {
        logger.info("开始查询数据");
        Role role;
        if (type instanceof Integer) {
            int id = (int)type;
            role = roleMapper.selectRoleByID(id);
        } else {
            String name = (String)type;
            role = roleMapper.selectRoleByName(name);
        }
        return role.toString();
    }

    public int insertRole(Role params) {
        logger.info("开始提交数据");
        int result = roleMapper.insertRole(params);
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

    public int deleteRole(String name) {
        logger.info("开始删除数据");
        int result = roleMapper.deleteRole(name);
        System.out.println("删除数据结果: " + result);
        // insert返回结果为 1，表示插入了一条数据
        return result;
    }

    public int updateRole(Role params) {
        logger.info("开始修改数据");
        int result = roleMapper.updateRole(params);
        System.out.println("修改数据结果: " + result);
        return result;
    }

}
