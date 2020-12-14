package com.zhang.contract.service;

import com.zhang.contract.entity.Role;
import com.zhang.contract.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    public Role selectRole(Object type) {
        Role role;
        if (type instanceof Integer) {
            int id = (int)type;
            role = roleMapper.selectRoleByID(id);
        } else {
            String name = (String)type;
            role = roleMapper.selectRoleByName(name);
        }
        return role;
    }

    public int insertRole(Role params) {
        try {
            //能获取插入的id是因为UserMapper.xml的insert语句新增了useGeneratedKeys和keyProperty参数
            roleMapper.insertRole(params);
            return params.getId();
        } catch (Exception e) {
            return -1;
        }
    }

    public int deleteRole(String name) {
        return roleMapper.deleteRole(name);
    }

    public int updateRole(Role params) {
        return roleMapper.updateRole(params);
    }
}
