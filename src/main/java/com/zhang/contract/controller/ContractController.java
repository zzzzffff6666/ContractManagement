package com.zhang.contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.Contract;
import com.zhang.contract.service.ContractService;
import com.zhang.contract.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractController {
    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;

    @Autowired
    private LogService logService;

    @RequestMapping(value= {"selectAll"}, method={RequestMethod.GET})
    public List<Contract> selectAllContract() {
        logger.info("查询数据所有记录: ");
        List<Contract> result = contractService.selectAll();
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"selectByID/{id}"}, method={RequestMethod.GET})
    public Contract selectContractByID(@PathVariable int id) {
        logger.info("查询数据ID为: " + id);
        Contract result = contractService.selectContract(id);
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"selectByName/{name}"}, method={RequestMethod.GET})
    public Contract selectContractByName(@PathVariable String name) {
        logger.info("查询数据name为: " + name);
        Contract result = contractService.selectContract(name);
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"delete/{name}"}, method={RequestMethod.GET})
    public int deleteContract(@PathVariable String name) {
        logger.info("删除数据name为: " + name);
        int result = contractService.deleteContract(name);
        logger.info("删除数据成功");
        return result;
    }

    @RequestMapping(value= {"update"}, method={RequestMethod.POST})
    public String updateContract(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Contract contract = new Contract();
        contract.setId(rootNode.path("id").asInt());
        contract.setName(rootNode.path("name").asText());
        contract.setCustomer_name(rootNode.path("customer_name").asText());
        contract.setBegin_time(logService.strToDate(rootNode.path("begin_time").asText()));
        contract.setEnd_time(logService.strToDate(rootNode.path("end_time").asText()));
        contract.setContent(rootNode.path("content").asText());
        contract.setUser_name(rootNode.path("user_name").asText());
        logger.info("数据转为实体bean成功");
        int result = contractService.updateContract(contract);
        if (result != 0) {
            logger.info("数据修改成功");
            return "Commit Success";
        } else {
            logger.info("数据修改失败");
            return "Commit Fail";
        }
    }

    @RequestMapping(value= {"insert"}, method={RequestMethod.POST})
    public int insertContract(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Contract contract = new Contract();
        contract.setId(rootNode.path("id").asInt());
        contract.setName(rootNode.path("name").asText());
        contract.setCustomer_name(rootNode.path("customer_name").asText());
        contract.setBegin_time(logService.strToDate(rootNode.path("begin_time").asText()));
        contract.setEnd_time(logService.strToDate(rootNode.path("end_time").asText()));
        contract.setContent(rootNode.path("content").asText());
        contract.setUser_name(rootNode.path("user_name").asText());
        logger.info("数据转为实体bean成功");
        int result = contractService.insertContract(contract);
        if (result != -1) {
            logger.info("数据入库成功");
        } else {
            logger.info("数据入库失败");
        }
        return result;
    }
}
