package com.zhang.contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.Customer;
import com.zhang.contract.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value= {"selectAll"}, method={RequestMethod.GET})
    public List<Customer> selectAllCustomer() {
        logger.info("查询数据所有记录: ");
        List<Customer> result = customerService.selectAll();
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"selectByID/{id}"}, method={RequestMethod.GET})
    public Customer selectCustomerByID(@PathVariable int id) {
        logger.info("查询数据ID为: " + id);
        Customer result = customerService.selectCustomer(id);
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"selectByName/{name}"}, method={RequestMethod.GET})
    public Customer selectCustomerByName(@PathVariable String name) {
        logger.info("查询数据name为: " + name);
        Customer result = customerService.selectCustomer(name);
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"delete/{name}"}, method={RequestMethod.GET})
    public int deleteCustomer(@PathVariable String name) {
        logger.info("删除数据name为: " + name);
        int result = customerService.deleteCustomer(name);
        logger.info("删除数据成功");
        return result;
    }

    @RequestMapping(value= {"update"}, method={RequestMethod.POST})
    public String updateCustomer(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Customer customer = new Customer();
        customer.setId(rootNode.path("id").asInt());
        customer.setName(rootNode.path("name").asText());
        customer.setAddress(rootNode.path("address").asText());
        customer.setTel(rootNode.path("tel").asText());
        customer.setFax(rootNode.path("fax").asText());
        customer.setCode(rootNode.path("code").asText());
        customer.setBank(rootNode.path("bank").asText());
        customer.setAccount(rootNode.path("account").asText());
        logger.info("数据转为实体bean成功");
        int result = customerService.updateCustomer(customer);
        if (result != 0) {
            logger.info("数据修改成功");
            return "Commit Success";
        } else {
            logger.info("数据修改失败");
            return "Commit Fail";
        }
    }

    @RequestMapping(value= {"insert"}, method={RequestMethod.POST})
    public int insertCustomer(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Customer customer = new Customer();
        customer.setId(rootNode.path("id").asInt());
        customer.setName(rootNode.path("name").asText());
        customer.setAddress(rootNode.path("address").asText());
        customer.setTel(rootNode.path("tel").asText());
        customer.setFax(rootNode.path("fax").asText());
        customer.setCode(rootNode.path("code").asText());
        customer.setBank(rootNode.path("bank").asText());
        customer.setAccount(rootNode.path("account").asText());
        logger.info("数据转为实体bean成功");
        int result = customerService.insertCustomer(customer);
        if (result != -1) {
            logger.info("数据入库成功");
        } else {
            logger.info("数据入库失败");
        }
        return result;
    }
}
