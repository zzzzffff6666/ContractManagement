package com.zhang.contract.service;

import com.zhang.contract.entity.Contract;
import com.zhang.contract.mapper.ContractMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContractService {

    @Resource
    private ContractMapper contractMapper;

    public List<Contract> selectByUser(String user_name) {
        return contractMapper.selectContractByUser(user_name);
    }

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
        try {
            //能获取插入的id是因为UserMapper.xml的insert语句新增了useGeneratedKeys和keyProperty参数
            contractMapper.insertContract(params);
            return params.getId();
        } catch (Exception e) {
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
