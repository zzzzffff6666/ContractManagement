package com.zhang.contract.mapper;

import com.zhang.contract.entity.Log;

import java.util.List;

public interface LogMapper {
    public int addLog(Log log);

    public List<Log> getAllLog();
}
