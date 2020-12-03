package com.zhang.contract.mapper;

import com.zhang.contract.entity.Contract;

import java.util.List;

public interface ContractMapper {
    public Contract selectContractByID(Integer id);

    public Contract selectContractByName(String name);

    public List<Contract> selectAll();

    public int insertContract(Contract params);

    public int updateContract(Contract params);

    public int deleteContract(String name);
}
