package com.zhang.contract.mapper;

import com.zhang.contract.entity.Role;

import java.util.List;

public interface RoleMapper {
    public Role selectRoleByID(Integer id);

    public Role selectRoleByName(String name);

    public List<Role> selectAll();

    public int insertRole(Role params);

    public int updateRole(Role params);

    public int deleteRole(String name);
}
