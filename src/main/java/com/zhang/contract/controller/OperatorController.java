package com.zhang.contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.*;
import com.zhang.contract.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/operate")
public class OperatorController {
    private static final Logger logger = LoggerFactory.getLogger(OperatorController.class);

    @Autowired
    private ContractService contractService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private StateService stateService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    //起草合同
    @RequestMapping(value = "/contract/draft", method = RequestMethod.POST)
    public String draft(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Contract contract = new Contract();
        contract.setName(rootNode.findValue("name").asText());
        contract.setUser_name(rootNode.findValue("user_name").asText());
        contract.setCustomer_name(rootNode.findValue("customer_name").asText());
        contract.setContent(rootNode.findValue("content").asText());

        logger.info(contract.getContent() + "===" + contract.getUser_name());
        Iterator<JsonNode> date = rootNode.findValue("date").elements();
        contract.setBegin_time(date.next().toString());
        contract.setEnd_time(date.next().toString());

        if (contractService.insertContract(contract) != -1) {
            logger.info("新建合同成功");
            //插入state表
            State s = new State();
            s.setCon_name(contract.getName());
            s.setType(1);
            s.setTime(logService.currentDate());
            stateService.insertState(s);

            Log log = new Log();
            log.setUser_name(contract.getUser_name());
            log.setContent("起草了一个合同：" + contract.getName());
            log.setTime(s.getTime());
            logService.log(log);

            return "Draft success";
        }
        return "Draft failure";
    }

    //获取当前用户的待会签列表
    @RequestMapping(value = "/contract/getCountersign", method = RequestMethod.POST)
    public List<Processes> getCounterSign() {
        User u = new User();

        return processService.selectProcessByUserAndType(u.getName(), 1);
    }

    //会签
    @RequestMapping(value = "/contract/countersign", method = RequestMethod.POST)
    public String countSign(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Processes process = new Processes();
        process.setCon_name(rootNode.findValue("con_name").asText());
        process.setUser_name(rootNode.findValue("user_name").asText());
        process.setContent(rootNode.findValue("content").asText());
        process.setType(1);
        process.setState(rootNode.findValue("state").asInt());
        process.setTime(logService.currentDate());

        if (processService.updateProcess(process) == 1) {
            logger.info("会签合同成功");
            if (processService.isProcessFinish(process.getCon_name(), process.getType())) {
                State s = new State();
                s.setCon_name(process.getCon_name());
                s.setType(2);
                s.setTime(process.getTime());
                stateService.updateState(s);
            }

            Log log = new Log();
            User user = new User();
            log.setUser_name(user.getName());
            log.setContent("会签了一个合同：" + process.getCon_name());
            log.setTime(logService.currentDate());
            logService.log(log);

            return "Count sign successful";
        } else {
            return "Count sign failure";
        }
    }

    //获取当前用户的待定稿列表
    @RequestMapping(value = "/contract/getFinalize", method = RequestMethod.POST)
    public List<Contract> getFinalize() {
        User u = new User();

        List<Contract> cs = contractService.selectByUser(u.getName());
        List<Contract> result = new ArrayList<>();
        for (Contract c : cs) {
            if (stateService.selectState(c.getName()).getType() == 2) {
                result.add(c);
            }
        }
        return result;
    }

    //定稿
    @RequestMapping(value = "/contract/finalize", method = RequestMethod.POST)
    public String finalize(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Contract contract = new Contract();
        contract.setName(rootNode.findValue("name").asText());
        contract.setUser_name(rootNode.findValue("user_name").asText());
        contract.setCustomer_name(rootNode.findValue("customer_name").asText());
        contract.setContent(rootNode.findValue("content").asText());

        logger.info(contract.getContent() + "===" + contract.getUser_name());

        Iterator<JsonNode> date = rootNode.findValue("date").elements();
        contract.setBegin_time(date.next().toString());
        contract.setEnd_time(date.next().toString());
        if (contractService.updateContract(contract) == 1) {
            logger.info("定稿合同成功");
            State s = new State();
            s.setCon_name(contract.getName());
            s.setTime(logService.currentDate());
            s.setType(3);
            stateService.updateState(s);

            Log log = new Log();
            log.setUser_name(contract.getUser_name());
            log.setContent("定稿了一个合同：" + contract.getName());
            log.setTime(s.getTime());
            logService.log(log);

            return "Finalize success";
        } else return "Finalize failure";
    }
    
    //获取当前用户的待审批列表
    @RequestMapping(value = "/contract/getApproval", method = RequestMethod.POST)
    public List<Processes> getApproval() {
        User u = new User();

        return processService.selectProcessByUserAndType(u.getName(), 2);
    }

    //审批
    @RequestMapping(value = "/contract/approval", method = RequestMethod.POST)
    public String approval(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Processes process = new Processes();
        process.setCon_name(rootNode.findValue("con_name").asText());
        process.setUser_name(rootNode.findValue("user_name").asText());
        process.setContent(rootNode.findValue("content").asText());
        process.setType(2);
        process.setState(rootNode.findValue("state").asInt());
        process.setTime(logService.currentDate());

        if (processService.updateProcess(process) == 1) {
            logger.info("审批合同成功");
            if (processService.isProcessFinish(process.getCon_name(), 2)) {
                State s = new State();
                s.setCon_name(process.getCon_name());
                s.setType(4);
                s.setTime(process.getTime());
                stateService.updateState(s);
            }

            Log log = new Log();
            User user = new User();
            log.setUser_name(user.getName());
            log.setContent("审批了一个合同：" + process.getCon_name());
            log.setTime(logService.currentDate());
            logService.log(log);

            return "Approval successful";
        } else {
            return "Approval failure";
        }
    }

    //获取当前用户的待签订列表
    @RequestMapping(value = "/contract/getSign")
    public List<Processes> getSign() {
        User u = new User();

        return processService.selectProcessByUserAndType(u.getName(), 3);
    }

    //签订
    @RequestMapping(value = "/contract/sign", method = RequestMethod.POST)
    public String sign(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Processes process = new Processes();
        process.setCon_name(rootNode.findValue("con_name").asText());
        process.setUser_name(rootNode.findValue("user_name").asText());
        process.setContent(rootNode.findValue("content").asText());
        process.setType(3);
        process.setState(rootNode.findValue("state").asInt());
        process.setTime(logService.currentDate());

        if (processService.updateProcess(process) == 1) {
            logger.info("签订合同成功");
            if (processService.isProcessFinish(process.getCon_name(), 3)) {
                State s = new State();
                s.setCon_name(process.getCon_name());
                s.setType(5);
                s.setTime(process.getTime());
                stateService.updateState(s);
            }

            Log log = new Log();
            User user = new User();
            log.setUser_name(user.getName());
            log.setContent("签订了一个合同：" + process.getCon_name());
            log.setTime(logService.currentDate());
            logService.log(log);

            return "Sign successful";
        } else {
            return "Sign failure";
        }
    }

    //获取当前用户所有合同
    @RequestMapping(value = "/contract/getUserContract", method = RequestMethod.POST)
    public List<Contract> getUserAllContract() {
        User u = new User();

        List<Contract> cs = contractService.selectByUser(u.getName());
        return cs;
    }

    //获取所有合同
    @RequestMapping(value = "/contract/all", method = RequestMethod.POST)
    public List<Contract> getAllContract() {
        return contractService.selectAll();
    }

    //获取合同的状态
    @RequestMapping(value = "contract/getState", method = RequestMethod.POST)
    public State getContractState(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String con_name = rootNode.findValue("con_name").asText();
        return stateService.selectState(con_name);
    }

    //获取合同的操作过程
    @RequestMapping(value = "contract/getProcess", method = RequestMethod.POST)
    public List<Processes> getContractProcess(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String con_name = rootNode.findValue("con_name").asText();
        return processService.selectProcess(con_name);
    }

    //删除一个合同
    @RequestMapping(value = "contract/delete", method = RequestMethod.POST)
    public String deleteContract(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String con_name = rootNode.findValue("con_name").asText();
        if (processService.deleteProcess(con_name) <= 0) return "Delete failure";
        if (stateService.deleteState(con_name) <= 0) return "Delete failure";
        if (contractService.deleteContract(con_name) <= 0) return "Delete failure";
        return "Delete success";
    }




}
