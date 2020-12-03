package com.zhang.contract.mapper;

import com.zhang.contract.entity.State;

public interface StateMapper {

    public State selectState(int con_id);

    public int insertState(State params);

    public int updateState(State params);

    public int deleteState(int con_id);
}
