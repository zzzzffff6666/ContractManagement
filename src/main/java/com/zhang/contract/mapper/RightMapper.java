package com.zhang.contract.mapper;

import com.zhang.contract.entity.Right;

import java.util.List;

public interface RightMapper {

    public List<String> selectRightByRoleName(String name);

    public List<String> selectRightByUserName(String name);

    public int insertRight(Right params);

    public int deleteRight(String name);
}
