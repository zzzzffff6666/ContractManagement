package com.zhang.contract.service;

import com.zhang.contract.entity.State;
import com.zhang.contract.mapper.StateMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StateService {

    @Resource
    private StateMapper stateMapper;

    public List<State> selectByType(int type) {
        return stateMapper.selectByType(type);
    }

    public State selectState(String con_name) {
        return stateMapper.selectState(con_name);
    }

    public int insertState(State params) {
        return stateMapper.insertState(params);
    }

    public int updateState(State params) {
        return stateMapper.updateState(params);
    }

    public int deleteState(String con_name) {
        return stateMapper.deleteState(con_name);
    }
}
