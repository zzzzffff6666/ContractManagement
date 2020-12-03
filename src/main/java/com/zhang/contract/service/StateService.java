package com.zhang.contract.service;

import com.zhang.contract.entity.State;
import com.zhang.contract.mapper.StateMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StateService {

    @Resource
    private StateMapper stateMapper;

    public State selectState(int con_id) {
        return stateMapper.selectState(con_id);
    }

    public int insertState(State params) {
        return stateMapper.insertState(params);
    }

    public int updateState(State params) {
        return stateMapper.updateState(params);
    }

    public int deleteState(int con_id) {
        return stateMapper.deleteState(con_id);
    }
}
