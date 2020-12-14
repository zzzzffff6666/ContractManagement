package com.zhang.contract.service;

import com.zhang.contract.entity.Customer;
import com.zhang.contract.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    public List<Customer> selectAll() {
        return customerMapper.selectAll();
    }

    public Customer selectCustomer(Object type) {
        Customer customer;
        if (type instanceof Integer) {
            int id = (int)type;
            customer = customerMapper.selectCustomerByID(id);
        } else {
            String name = (String)type;
            customer = customerMapper.selectCustomerByName(name);
        }
        return customer;
    }

    public int insertCustomer(Customer params) {
        try {
            //能获取插入的id是因为UserMapper.xml的insert语句新增了useGeneratedKeys和keyProperty参数
            customerMapper.insertCustomer(params);
            return params.getId();
        } catch (Exception e) {
            return -1;
        }
    }

    public int deleteCustomer(String name) {
        return customerMapper.deleteCustomer(name);
    }

    public int updateCustomer(Customer params) {
        return customerMapper.updateCustomer(params);
    }
}
