package com.zhang.contract.service;

import com.zhang.contract.entity.Contract;
import com.zhang.contract.mapper.ContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ContractService {
    private static final Logger logger = LoggerFactory.getLogger(ContractService.class);

    @Resource
    private ContractMapper contractMapper;

    public List<Contract> selectAll() {
        logger.info("开始查询数据");
        return contractMapper.selectAll();
    }

    public String selectContract(Object type) {
        logger.info("开始查询数据");
        Contract contract;
        if (type instanceof Integer) {
            int id = (int)type;
            contract = contractMapper.selectContractByID(id);
        } else {
            String name = (String)type;
            contract = contractMapper.selectContractByName(name);
        }
        return contract.toString();
    }

    public int insertContract(Contract params) {
        logger.info("开始提交数据");
        int result = contractMapper.insertContract(params);
        //能获取插入的id是因为UserMapper.xml的insert语句新增了useGeneratedKeys和keyProperty参数
        Integer insertId = params.getId();
        System.out.println("插入数据的ID: " + insertId);
        System.out.println("insert结果: " + result);
        if (result == 1) {
            return insertId;
        } else {
            return 0;
        }
    }

    public int deleteContract(String name) {
        logger.info("开始删除数据");
        int result = contractMapper.deleteContract(name);
        System.out.println("删除数据结果: " + result);
        // insert返回结果为 1，表示插入了一条数据
        return result;
    }

    public int updateContract(Contract params) {
        logger.info("开始修改数据");
        int result = contractMapper.updateContract(params);
        System.out.println("修改数据结果: " + result);
        return result;
    }

    public Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = new Date(format.parse(str).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
