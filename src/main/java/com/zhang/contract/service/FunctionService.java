package com.zhang.contract.service;

import com.zhang.contract.entity.Function;
import com.zhang.contract.mapper.FunctionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FunctionService {

    @Resource
    private FunctionMapper functionMapper;

    public List<Function> selectAll() {
        return functionMapper.selectAll();
    }

    public Function selectFunctionByID(Integer id) {
        return functionMapper.selectFunctionByID(id);
    }

    public List<Function> selectFunctionByList(List<Integer> idList) {
        return functionMapper.selectFunctionByList(idList);
    }
}
