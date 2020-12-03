package com.zhang.contract.mapper;

import com.zhang.contract.entity.Customer;

import java.util.List;

public interface CustomerMapper {
    public Customer selectCustomerByID(Integer id);

    public Customer selectCustomerByName(String name);

    public List<Customer> selectAll();

    public int insertCustomer(Customer params);

    public int updateCustomer(Customer params);

    public int deleteCustomer(String name);
}
