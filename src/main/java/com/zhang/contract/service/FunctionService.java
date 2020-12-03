package com.zhang.contract.service;

import com.zhang.contract.entity.Function;
import com.zhang.contract.mapper.FunctionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FunctionService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Resource
    private FunctionMapper functionMapper;

    public List<Function> selectAll() {
        logger.info("开始查询数据");
        return functionMapper.selectAll();
    }

    public Function selectFunctionByID(Integer id) {
        logger.info("开始查询数据");
        Function function = functionMapper.selectFunctionByID(id);
        return function;
    }

    public List<Function> selectFunctionByList(List<Integer> idList) {
        logger.info("开始查询数据");
        List<Function> functions = functionMapper.selectFunctionByList(idList);
        return functions;
    }
}
