package com.zhang.contract.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.*;
import com.zhang.contract.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManagerController {
    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private RightService rightService;
    @Autowired
    private LogService logService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private FunctionService functionService;

    @PostMapping("/role/add")
    public String createRole(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Role role = new Role();
        role.setName(rootNode.findValue("name").asText());
        role.setDescription(rootNode.findValue("description").asText());
        role.setFunctions(rootNode.findValue("functions").asText());

        if (roleService.insertRole(role) != -1) {
            logger.info("添加角色成功！");
            return "Add success";
        } else {
            logger.info("添加角色失败！");
            return "Add failure";
        }
    }

    @PostMapping("/role/delete")
    public String deleteRole(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String name = rootNode.findValue("name").asText();

        if (roleService.deleteRole(name) > 0) {
            logger.info("删除角色成功");
            return "Delete success";
        } else {
            logger.info("删除角色失败");
            return "Delete failure";
        }
    }

    @PostMapping("/role/modify")
    public String modifyRole(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Role role = new Role();
        role.setName(rootNode.findValue("name").asText());
        role.setDescription(rootNode.findValue("description").asText());
        role.setFunctions(rootNode.findValue("functions").asText());

        if (roleService.updateRole(role) > 0) {
            logger.info("修改角色成功！");
            return "Modify success";
        } else {
            logger.info("修改角色失败！");
            return "Modify failure";
        }
    }

    @PostMapping("/role/query")
    public Role queryRole(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String name = rootNode.findValue("name").asText();

        Role role = roleService.selectRole(name);
        if (role == null) {
            logger.info("未找到该角色");
            role = new Role();
            role.setId(-1);
        } else {
            logger.info("已找到该角色");
        }
        return role;
    }

    @PostMapping("/role/all")
    public List<Role> getAllRole(@RequestBody String params) {
        logger.info("查找所有角色");
        return roleService.selectAll();
    }

    @PostMapping("/customer/add")
    public String createCustomer(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Customer customer = new Customer();
        customer.setName(rootNode.findValue("name").asText());
        customer.setAddress(rootNode.findValue("address").asText());
        customer.setTel(rootNode.findValue("tel").asText());
        customer.setFax(rootNode.findValue("fax").asText());
        customer.setCode(rootNode.findValue("code").asText());
        customer.setBank(rootNode.findValue("bank").asText());
        customer.setAccount(rootNode.findValue("account").asText());

        if (customerService.insertCustomer(customer) != -1) {
            logger.info("添加客户成功！");
            return "Add success";
        } else {
            logger.info("添加客户失败！");
            return "Add failure";
        }
    }

    @PostMapping("/customer/delete")
    public String deleteCustomer(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String name = rootNode.findValue("name").asText();

        if (customerService.deleteCustomer(name) > 0) {
            logger.info("删除客户成功");
            return "Delete success";
        } else {
            logger.info("删除客户失败");
            return "Delete failure";
        }
    }

    @PostMapping("/customer/modify")
    public String modifyCustomer(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Customer customer = new Customer();
        customer.setName(rootNode.findValue("name").asText());
        customer.setAddress(rootNode.findValue("address").asText());
        customer.setTel(rootNode.findValue("tel").asText());
        customer.setFax(rootNode.findValue("fax").asText());
        customer.setCode(rootNode.findValue("code").asText());
        customer.setBank(rootNode.findValue("bank").asText());
        customer.setAccount(rootNode.findValue("account").asText());

        if (customerService.updateCustomer(customer) > 0) {
            logger.info("修改客户成功！");
            return "Modify success";
        } else {
            logger.info("修改客户失败！");
            return "Modify failure";
        }
    }

    @PostMapping("/customer/query")
    public Customer queryCustomer(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String name = rootNode.findValue("name").asText();

        Customer customer = customerService.selectCustomer(name);
        if (customer == null) {
            logger.info("未找到该客户");
            customer = new Customer();
            customer.setId(-1);
        } else {
            logger.info("已找到该客户");
        }
        return customer;
    }

    @PostMapping("/customer/all")
    public List<Customer> getAllCustomer() {
        logger.info("查找所有客户");
        return customerService.selectAll();
    }

    @PostMapping("/user/add")
    public String createUser(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        User user = new User();
        user.setName(rootNode.findValue("name").asText());
        user.setPassword(rootNode.findValue("password").asText());

        if (userService.insertUser(user) != -1) {
            logger.info("添加用户成功！");
            return "Add success";
        } else {
            logger.info("添加用户失败！");
            return "Add failure";
        }
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String name = rootNode.findValue("name").asText();

        if (userService.deleteUser(name) > 0) {
            logger.info("删除用户成功");
            return "Delete success";
        } else {
            logger.info("删除用户失败");
            return "Delete failure";
        }
    }

    @PostMapping("/user/modify")
    public String modifyUser(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        User user = new User();
        user.setName(rootNode.findValue("name").asText());
        user.setPassword(rootNode.findValue("password").asText());

        if (userService.updateUser(user) > 0) {
            logger.info("修改用户成功");
            return "Modify success";
        } else {
            logger.info("修改用户失败");
            return "Modify failure";
        }
    }

    @PostMapping("/user/query")
    public User queryUser(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String name = rootNode.findValue("name").asText();

        User user = userService.selectUser(name);
        if (user == null) {
            logger.info("未找到该用户");
            user = new User();
            user.setId(-1);
        } else {
            logger.info("已找到该用户");
        }
        return user;
    }

    @PostMapping("/user/all")
    public List<User> getAllUser() {
        logger.info("查找所有用户");
        return userService.selectAll();
    }

    @PostMapping("/assign/role")
    public String assignRole(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String user_name = rootNode.findValue("user_name").asText();
        List<String> roles1 = new ArrayList<>();
        List<String> roles2 = new ArrayList<>();

        //List 链表版
        List<JsonNode> lj = rootNode.findParents("roles");
        for (JsonNode jn : lj) {
            roles1.add(jn.asText());
        }

        //Array 数组版
        Iterator<JsonNode> ij = rootNode.findValue("roles").elements();
        while (ij.hasNext()) {
            roles2.add(ij.next().asText());
        }

        List<Right> rights = new ArrayList<>();
        for (String role : roles1) {
            Right right = new Right();
            right.setUser_name(user_name);
            right.setRole_name(role);
            right.setDescription("将" + role + "权限配置给" + user_name);
            rights.add(right);
        }
        rightService.updateRight(rights);

        return "权限配置成功";
    }

    @PostMapping("/assign/countersign")
    public String assignCountersign(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String con_name = rootNode.findValue("con_name").asText();
        List<String> users1 = new ArrayList<>();
        List<String> users2 = new ArrayList<>();

        //List 链表版
        List<JsonNode> lj = rootNode.findParents("users");
        for (JsonNode jn : lj) {
            users1.add(jn.asText());
        }

        //Array 数组版
        Iterator<JsonNode> ij = rootNode.findValue("users").elements();
        while (ij.hasNext()) {
            users2.add(ij.next().asText());
        }

        for (String user : users1) {
            Processes process = new Processes();
            process.setCon_name(con_name);
            process.setUser_name(user);
            process.setType(1);
            process.setState(0);
            process.setTime(logService.currentDate());
            process.setContent("还没有进行任何操作");
            processService.insertProcess(process);
        }

        return "分配会签成功";
    }

    @PostMapping("/assign/audit")
    public String assignAudit(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String con_name = rootNode.findValue("con_name").asText();
        List<String> users1 = new ArrayList<>();
        List<String> users2 = new ArrayList<>();

        //List 链表版
        List<JsonNode> lj = rootNode.findParents("users");
        for (JsonNode jn : lj) {
            users1.add(jn.asText());
        }

        //Array 数组版
        Iterator<JsonNode> ij = rootNode.findValue("users").elements();
        while (ij.hasNext()) {
            users2.add(ij.next().asText());
        }

        for (String user : users1) {
            Processes process = new Processes();
            process.setCon_name(con_name);
            process.setUser_name(user);
            process.setType(2);
            process.setState(0);
            process.setTime(logService.currentDate());
            process.setContent("还没有进行任何操作");
            processService.insertProcess(process);
        }

        return "分配审批成功";
    }

    @PostMapping("/assign/sign")
    public String assignSign(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String con_name = rootNode.findValue("con_name").asText();
        List<String> users1 = new ArrayList<>();
        List<String> users2 = new ArrayList<>();

        //List 链表版
        List<JsonNode> lj = rootNode.findParents("users");
        for (JsonNode jn : lj) {
            users1.add(jn.asText());
        }

        //Array 数组版
        Iterator<JsonNode> ij = rootNode.findValue("users").elements();
        while (ij.hasNext()) {
            users2.add(ij.next().asText());
        }

        for (String user : users1) {
            Processes process = new Processes();
            process.setCon_name(con_name);
            process.setUser_name(user);
            process.setType(3);
            process.setState(0);
            process.setTime(logService.currentDate());
            process.setContent("还没有进行任何操作");
            processService.insertProcess(process);
        }

        return "分配签订成功";
    }

    @PostMapping("/getRoleFunction")
    public List<Function> getRoleFunction(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String role_name = rootNode.findValue("role_name").asText();

        Role role = roleService.selectRole(role_name);
        if (role == null) {
            logger.info("未找到该角色");
            return null;
        }
        List<Integer> functions = role.getFunctionList();
        return functionService.selectFunctionByList(functions);
    }

    @PostMapping("/getUserRole")
    public List<Role> getUserRole(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");

        String name = rootNode.findValue("name").asText();
        List<String> rights = rightService.selectRight(name);

        List<Role> roles = new ArrayList<>();
        for (String role_name : rights) {
            roles.add(roleService.selectRole(role_name));
        }
        return roles;
    }

}
