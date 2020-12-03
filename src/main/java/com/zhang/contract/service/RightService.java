package com.zhang.contract.service;

import com.zhang.contract.entity.Right;
import com.zhang.contract.mapper.RightMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RightService {

    @Resource
    private RightMapper rightMapper;

    public List<Right> selectRight(String name) {
        return rightMapper.selectRightByUserName(name);
    }

    public int insertRight(Right params) {
        return rightMapper.insertRight(params);
    }

    public int deleteRight(String name) {
        return rightMapper.deleteRight(name);
    }

    public int updateRight(List<Right> params) {
        int result = 0;
        deleteRight(params.get(0).getUser_name());
        for (Right r : params) {
            result += insertRight(r);
        }
        return result;
    }
}
