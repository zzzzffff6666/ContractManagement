package com.zhang.contract.mapper;

import com.zhang.contract.entity.Function;

import java.util.List;

public interface FunctionMapper {
    public Function selectFunctionByName(String name);

    public Function selectFunctionByID(Integer id);

    public List<Function> selectFunctionByList(List<Integer> fList);

    public List<Function> selectAll();
}
