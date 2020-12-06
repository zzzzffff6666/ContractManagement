package com.zhang.contract.mapper;

import com.zhang.contract.entity.Processes;

import java.util.List;

public interface ProcessMapper {

    public List<Processes> selectForChange(String con_name, int type);

    public List<Processes> selectByUserType(String user_name, int type);

    public List<Processes> selectProcess(String con_name);

    public int insertProcess(Processes params);

    public int updateProcess(Processes params);

    public int deleteProcess(String con_name);
}
