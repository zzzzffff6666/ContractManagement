package com.zhang.contract.service;

import com.zhang.contract.entity.Customer;
import com.zhang.contract.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Resource
    private CustomerMapper customerMapper;

    public List<Customer> selectAll() {
        logger.info("开始查询数据");
        return customerMapper.selectAll();
    }

    public Customer selectCustomer(Object type) {
        logger.info("开始查询数据");
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
        logger.info("开始提交数据");
        int result = customerMapper.insertCustomer(params);
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

    public int deleteCustomer(String name) {
        logger.info("开始删除数据");
        int result = customerMapper.deleteCustomer(name);
        System.out.println("删除数据结果: " + result);
        // insert返回结果为 1，表示插入了一条数据
        return result;
    }

    public int updateCustomer(Customer params) {
        logger.info("开始修改数据");
        int result = customerMapper.updateCustomer(params);
        System.out.println("修改数据结果: " + result);
        return result;
    }
}
