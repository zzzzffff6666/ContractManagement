package com.zhang.contract.service;

import com.zhang.contract.entity.Contract;
import com.zhang.contract.mapper.ContractMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ContractService {

    @Resource
    private ContractMapper contractMapper;

    public List<Contract> selectAll() {
        return contractMapper.selectAll();
    }

    public Contract selectContract(Object type) {
        Contract contract;
        if (type instanceof Integer) {
            int id = (int)type;
            contract = contractMapper.selectContractByID(id);
        } else {
            String name = (String)type;
            contract = contractMapper.selectContractByName(name);
        }
        return contract;
    }

    public int insertContract(Contract params) {
        //能获取插入的id是因为UserMapper.xml的insert语句新增了useGeneratedKeys和keyProperty参数
        if (contractMapper.insertContract(params) == 1) {
            return params.getId();
        } else {
            return -1;
        }
    }

    public int deleteContract(String name) {
        return contractMapper.deleteContract(name);
    }

    public int updateContract(Contract params) {
        return contractMapper.updateContract(params);
    }
}
