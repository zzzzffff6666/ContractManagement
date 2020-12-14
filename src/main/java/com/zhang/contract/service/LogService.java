package com.zhang.contract.service;

import com.zhang.contract.entity.Log;
import com.zhang.contract.mapper.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LogService {
    @Resource
    private LogMapper logMapper;

    public int log(Log params) {
        return logMapper.addLog(params);
    }

    public List<Log> getAllLog() {
        return logMapper.getAllLog();
    }

    public String currentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
