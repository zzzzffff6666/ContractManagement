package com.zhang.contract.mapper;

import com.zhang.contract.entity.Processes;

import java.util.List;

public interface ProcessMapper {

    public List<Processes> selectProcess(int con_id);

    public int insertProcess(Processes params);

    public int updateProcess(Processes params);

    public int deleteProcess(int con_id);
}
