package com.zhang.contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.*;
import com.zhang.contract.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/operate")
public class OperatorController {
    private static final Logger logger = LoggerFactory.getLogger(OperatorController.class);

    @Resource
    private ContractService contractService;
    @Resource
    private AttachmentService attachmentService;
    @Resource
    private StateService stateService;
    @Resource
    private ProcessService processService;
    @Resource
    private LogService logService;

    //起草合同
    @RequestMapping(value = "/draft", method = RequestMethod.POST)
    public String draft(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(1)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        Contract contract = new Contract();
        contract.setName(rootNode.findValue("name").asText());
        contract.setUser_name(u.getName());
        contract.setCustomer_name(rootNode.findValue("customer_name").asText());
        contract.setContent(rootNode.findValue("content").asText());
        Iterator<JsonNode> date = rootNode.findValue("date").elements();
        contract.setBegin_time(date.next().toString());
        contract.setEnd_time(date.next().toString());

        if (contractService.insertContract(contract) != -1) {
            //插入state表
            State s = new State();
            s.setCon_name(contract.getName());
            s.setType(0);
            s.setTime(logService.currentDate());
            stateService.insertState(s);

            Log log = new Log();
            log.setUser_name(contract.getUser_name());
            log.setContent(u.getName() + " 起草了一个合同：" + contract.getName());
            log.setTime(s.getTime());
            logService.log(log);

            logger.info("新建合同成功");
            return "Draft success";
        }
        logger.info("新建合同失败");
        return "Draft failure";
    }

    //获取当前用户的待会签列表
    @RequestMapping(value = "/getCountersign", method = RequestMethod.POST)
    public Map<String, List> getCounterSign(HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(5)) throw new Exception("Hasn't right");

        logger.info("查询待会签列表");
        Map<String, List> result = new HashMap<>();
        result.put("result", processService.selectProcessByUserAndType(u.getName(), 1));
        return result;
    }

    //会签
    @RequestMapping(value = "/countersign", method = RequestMethod.POST)
    public String countSign(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(5)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        Processes process = new Processes();
        process.setCon_name(rootNode.findValue("name").asText());
        process.setUser_name(u.getName());
        process.setContent(rootNode.findValue("content").asText());
        process.setType(1);
        process.setState(1);
        process.setTime(logService.currentDate());

        if (processService.updateProcess(process) == 1) {
            if (processService.isProcessFinish(process.getCon_name(), process.getType())) {
                State s = new State();
                s.setCon_name(process.getCon_name());
                s.setType(2);
                s.setTime(process.getTime());
                stateService.updateState(s);
            }

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 会签了一个合同：" + process.getCon_name());
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("会签合同成功");
            return "Counter sign success";
        } else {
            logger.info("会签合同失败");
            return "Counter sign failure";
        }
    }

    //获取当前用户的待定稿列表
    @RequestMapping(value = "/getFinalize", method = RequestMethod.POST)
    public Map<String, List> getFinalize(HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(2)) throw new Exception("Hasn't right");

        List<Contract> cs = contractService.selectByUser(u.getName());
        List<Contract> contracts = new ArrayList<>();
        for (Contract c : cs) {
            State s = stateService.selectState(c.getName());
            if (s != null && s.getType() == 2) {
                contracts.add(c);
            }
        }

        logger.info("获取待定稿列表");
        Map<String, List> result = new HashMap<>();
        result.put("result", contracts);
        return result;
    }

    //定稿
    @RequestMapping(value = "/finalize", method = RequestMethod.POST)
    public String finalize(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(2)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        Contract contract = new Contract();
        contract.setName(rootNode.findValue("name").asText());
        contract.setUser_name(u.getName());
        contract.setCustomer_name(rootNode.findValue("user_name").asText());
        contract.setContent(rootNode.findValue("content").asText());

        Iterator<JsonNode> date = rootNode.findValue("date").elements();
        contract.setBegin_time(date.next().toString());
        contract.setEnd_time(date.next().toString());
        if (contractService.updateContract(contract) == 1) {
            State s = new State();
            s.setCon_name(contract.getName());
            s.setTime(logService.currentDate());
            s.setType(3);
            stateService.updateState(s);

            Log log = new Log();
            log.setUser_name(contract.getUser_name());
            log.setContent(u.getName() + " 定稿了一个合同：" + contract.getName());
            log.setTime(s.getTime());
            logService.log(log);

            logger.info("定稿合同成功");
            return "Finalize success";
        } else {
            logger.info("定稿合同失败");
            return "Finalize failure";
        }
    }
    
    //获取当前用户的待审批列表
    @RequestMapping(value = "/getAudit", method = RequestMethod.POST)
    public Map<String, List> getApproval(HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(6)) throw new Exception("Hasn't right");

        logger.info("获取待审批列表");
        Map<String, List> result = new HashMap<>();
        result.put("result", processService.selectProcessByUserAndType(u.getName(), 2));
        return result;
    }

    //审批
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public String approval(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(6)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        Processes process = new Processes();
        process.setCon_name(rootNode.findValue("name").asText());
        process.setUser_name(u.getName());
        process.setContent(rootNode.findValue("content").asText());
        process.setType(2);
        process.setState(rootNode.findValue("state").asInt());
        process.setTime(logService.currentDate());

        if (processService.updateProcess(process) == 1) {
            if (processService.isProcessFinish(process.getCon_name(), 2)) {
                State s = new State();
                s.setCon_name(process.getCon_name());
                s.setType(4);
                s.setTime(process.getTime());
                stateService.updateState(s);
            }

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 审批了一个合同：" + process.getCon_name());
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("审批合同成功");
            return "Audit success";
        } else {
            logger.info("审批合同失败");
            return "Audit failure";
        }
    }

    //获取当前用户的待签订列表
    @RequestMapping(value = "/getSign")
    public Map<String, List> getSign(HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(7)) throw new Exception("Hasn't right");

        logger.info("获取待定稿列表");
        Map<String, List> result = new HashMap<>();
        result.put("result", processService.selectProcessByUserAndType(u.getName(), 3));
        return result;
    }

    //签订
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public String sign(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(7)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        Processes process = new Processes();
        process.setCon_name(rootNode.findValue("name").asText());
        process.setUser_name(u.getName());
        process.setContent(rootNode.findValue("content").asText());
        process.setType(3);
        process.setState(1);
        process.setTime(logService.currentDate());

        if (processService.updateProcess(process) == 1) {
            if (processService.isProcessFinish(process.getCon_name(), 3)) {
                State s = new State();
                s.setCon_name(process.getCon_name());
                s.setType(5);
                s.setTime(process.getTime());
                stateService.updateState(s);
            }

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 签订了一个合同：" + process.getCon_name());
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("签订合同成功");
            return "Sign success";
        } else {
            logger.info("签订合同失败");
            return "Sign failure";
        }
    }

    //获取当前用户所有合同
    @RequestMapping(value = "/getUserContract", method = RequestMethod.POST)
    public Map<String, List> getUserAllContract(HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(1)) throw new Exception("Hasn't right");

        logger.info("查询用户起草的合同");
        List<Contract> cs = contractService.selectByUser(u.getName());
        Map<String, List> result = new HashMap<>();
        result.put("result", cs);
        return result;
    }

    //获取所有合同
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public Map<String, List> getAllContract(HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(3)) throw new Exception("Hasn't right");

        logger.info("查询所有合同");
        Map<String, List> result = new HashMap<>();
        result.put("result", contractService.selectAll());
        return result;
    }

    //获取合同信息
    @PostMapping(value = "/getContract")
    public Contract getContract(@RequestBody String params, HttpSession session) throws Exception{
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(3)) throw new Exception("Hasn't right");

        logger.info("查询合同信息");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String con_name = rootNode.findValue("name").asText();
        return contractService.selectContract(con_name);
    }

    //获取合同的状态
    @RequestMapping(value = "/getState", method = RequestMethod.POST)
    public State getContractState(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(8)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String con_name = rootNode.findValue("name").asText();

        State s = stateService.selectState(con_name);
        logger.info("查询合同状态");
        return s;
    }

    //获取合同的操作过程
    @RequestMapping(value = "/getProcess", method = RequestMethod.POST)
    public Map<String, List> getContractProcess(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(8)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String con_name = rootNode.findValue("name").asText();

        logger.info("查询合同流程");
        Map<String, List> result = new HashMap<>();
        result.put("result", processService.selectProcess(con_name));
        return result;
    }

    //删除一个合同
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteContract(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User)session.getAttribute("login");
        if (!u.hasRight(4)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String con_name = rootNode.findValue("name").asText();
        processService.deleteProcess(con_name);
        if (stateService.deleteState(con_name) <= 0) return "Delete failure";
        if (contractService.deleteContract(con_name) <= 0) return "Delete failure";

        Log log = new Log();
        log.setUser_name(u.getName());
        log.setContent(u.getName() + " 删除了一个合同：" + con_name);
        log.setTime(logService.currentDate());
        logService.log(log);

        logger.info("删除合同成功");
        return "Delete success";
    }
}
