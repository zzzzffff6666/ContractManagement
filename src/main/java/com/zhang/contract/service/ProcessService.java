package com.zhang.contract.service;

import com.zhang.contract.entity.Processes;
import com.zhang.contract.mapper.ProcessMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProcessService {

    @Resource
    private ProcessMapper processMapper;

    public List<Processes> selectProcess(int con_id) {
        return processMapper.selectProcess(con_id);
    }

    public int insertProcess(Processes params) {
        return processMapper.insertProcess(params);
    }

    public int updateProcess(Processes params) {
        return processMapper.updateProcess(params);
    }

    public int deleteProcess(int con_id) {
        return processMapper.deleteProcess(con_id);
    }
}
