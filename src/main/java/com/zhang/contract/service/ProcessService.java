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

    public boolean isProcessFinish(String con_name, int type) {
        if (processMapper.selectForChange(con_name, type).size() == 0) {
            return true;
        } else return false;
    }

    public List<Processes> selectProcessByUserAndType(String user_name, int type) {
        return processMapper.selectByUserType(user_name, type);
    }

    public List<Processes> selectProcess(String con_name) {
        return processMapper.selectProcess(con_name);
    }

    public int insertProcess(Processes params) {
        return processMapper.insertProcess(params);
    }

    public int updateProcess(Processes params) {
        return processMapper.updateProcess(params);
    }

    public int deleteProcess(String con_name) {
        return processMapper.deleteProcess(con_name);
    }


}
