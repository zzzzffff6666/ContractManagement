package com.zhang.contract.service;

import com.zhang.contract.entity.Log;
import com.zhang.contract.mapper.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class LogService {

    @Resource
    private LogMapper logMapper;

    public int log(Log params) {
        return logMapper.addLog(params);
    }

    public Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date = null;
        try {
            date = new Date(format.parse(str).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
