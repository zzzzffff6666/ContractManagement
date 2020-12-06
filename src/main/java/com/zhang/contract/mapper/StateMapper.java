package com.zhang.contract.mapper;

import com.zhang.contract.entity.State;

import java.util.List;

public interface StateMapper {

    public List<State> selectByType(int type);

    public State selectState(String con_name);

    public int insertState(State params);

    public int updateState(State params);

    public int deleteState(String con_name);
}
