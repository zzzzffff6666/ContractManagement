package com.zhang.contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.*;
import com.zhang.contract.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/manage")
public class ManagerController {
    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Resource
    private CustomerService customerService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserService userService;
    @Resource
    private RightService rightService;
    @Resource
    private LogService logService;
    @Resource
    private ProcessService processService;
    @Resource
    private FunctionService functionService;
    @Resource
    private StateService stateService;

    @PostMapping("/role/add")
    public String createRole(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(13)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        Role role = new Role();
        role.setName(rootNode.findValue("name").asText());
        role.setDescription(rootNode.findValue("description").asText());
        Iterator<JsonNode> f = rootNode.findValue("functions").elements();
        String fun = "";
        while (f.hasNext()) {
            Function function = functionService.selectFunction(f.next().asText());
            if (function != null) {
                fun += function.getId() + " ";
            }
        }
        role.setFunctions(fun);

        if (roleService.insertRole(role) != -1) {

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 添加了一个角色：" + role.getName());
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("添加角色成功！");
            return "Add success";
        } else {
            logger.info("添加角色失败！");
            return "Add failure";
        }
    }

    @PostMapping("/role/delete")
    public String deleteRole(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(14)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String name = rootNode.findValue("name").asText();

        if (roleService.deleteRole(name) > 0) {

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 删除了一个角色：" + name);
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("删除角色成功");
            return "Delete success";
        } else {
            logger.info("删除角色失败");
            return "Delete failure";
        }
    }

    @PostMapping("/role/modify")
    public String modifyRole(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(16)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        Role role = new Role();
        role.setName(rootNode.findValue("name").asText());
        role.setDescription(rootNode.findValue("description").asText());
        Iterator<JsonNode> it = rootNode.findValue("functions").elements();
        StringBuilder functions = new StringBuilder();
        while(it.hasNext()) {
            functions.append(it.next().asText()).append(" ");
        }
        role.setFunctions(functions.toString());
        System.out.println(role.getFunctions());

        if (roleService.updateRole(role) > 0) {

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 修改了一个角色：" + role.getName());
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("修改角色成功！");
            return "Modify success";
        } else {
            logger.info("修改角色失败！");
            return "Modify failure";
        }
    }

    @PostMapping("/role/query")
    public Map<String, Object> queryRole(@RequestBody String params, HttpSession session) throws Exception {
        if (!((User) session.getAttribute("login")).hasRight(15)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String name = rootNode.findValue("name").asText();

        Role role = roleService.selectRole(name);
        if (role == null) {
            logger.info("未找到该角色");
            throw new Exception("Cannot find");
        }
        logger.info("已找到该角色");
        Map<String, Object> result = new HashMap<>();
        result.put("role", role);
        List<Integer> functions = role.getFunctionList();
        result.put("funs", functionService.selectFunctionByList(functions));
        return result;
    }

    @PostMapping("/role/all")
    public Map<String, List> getAllRole(HttpSession session) throws Exception {
        if (!((User) session.getAttribute("login")).hasRight(15)) throw new Exception("Hasn't right");

        logger.info("查找所有角色");
        Map<String, List> result = new HashMap<>();
        result.put("result", roleService.selectAll());
        return result;
    }

    @PostMapping("/customer/add")
    public String createCustomer(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(17)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        Customer customer = new Customer();
        customer.setName(rootNode.findValue("name").asText());
        customer.setAddress(rootNode.findValue("address").asText());
        customer.setTel(rootNode.findValue("tel").asText());
        customer.setFax(rootNode.findValue("fax").asText());
        customer.setCode(rootNode.findValue("code").asText());
        customer.setBank(rootNode.findValue("bank").asText());
        customer.setAccount(rootNode.findValue("account").asText());

        if (customerService.insertCustomer(customer) != -1) {

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 添加了一个客户：" + customer.getName());
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("添加客户成功！");
            return "Add success";
        } else {
            logger.info("添加客户失败！");
            return "Add failure";
        }
    }

    @PostMapping("/customer/delete")
    public String deleteCustomer(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(18)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String name = rootNode.findValue("name").asText();

        if (customerService.deleteCustomer(name) > 0) {

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 删除了一个客户：" + name);
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("删除客户成功");
            return "Delete success";
        } else {
            logger.info("删除客户失败");
            return "Delete failure";
        }
    }

    @PostMapping("/customer/modify")
    public String modifyCustomer(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(20)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        Customer customer = new Customer();
        customer.setName(rootNode.findValue("name").asText());
        customer.setAddress(rootNode.findValue("address").asText());
        customer.setTel(rootNode.findValue("tel").asText());
        customer.setFax(rootNode.findValue("fax").asText());
        customer.setCode(rootNode.findValue("code").asText());
        customer.setBank(rootNode.findValue("bank").asText());
        customer.setAccount(rootNode.findValue("account").asText());

        if (customerService.updateCustomer(customer) > 0) {

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 修改了一个客户：" + customer.getName());
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("修改客户成功！");
            return "Modify success";
        } else {
            logger.info("修改客户失败！");
            return "Modify failure";
        }
    }

    @PostMapping("/customer/query")
    public Customer queryCustomer(@RequestBody String params, HttpSession session) throws Exception {
        if (!((User) session.getAttribute("login")).hasRight(19)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
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
    public Map<String, List> getAllCustomer(HttpSession session) throws Exception {
        if (!((User) session.getAttribute("login")).hasRight(19)) throw new Exception("Hasn't right");

        logger.info("查找所有客户");
        Map<String, List> result = new HashMap<>();
        result.put("result", customerService.selectAll());
        return result;
    }

    @PostMapping("/user/add")
    public String createUser(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(9)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        User user = new User();
        user.setName(rootNode.findValue("name").asText());
        user.setPassword(rootNode.findValue("password").asText());

        if (userService.insertUser(user) != -1) {
            List<String> roles = new ArrayList<>();
            //Array 数组版
            Iterator<JsonNode> ij = rootNode.findValue("roles").elements();
            if (ij != null) {
                while (ij.hasNext()) {
                    roles.add(ij.next().asText());
                }

                List<Right> rights = new ArrayList<>();
                for (String role : roles) {
                    Right right = new Right();
                    right.setUser_name(user.getName());
                    right.setRole_name(role);
                    right.setDescription("将" + role + "权限配置给" + user.getName());
                    rights.add(right);
                }
                rightService.updateRight(rights);
            }

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 添加了一个用户：" + user.getName());
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("添加用户成功！");
            return "Add success";
        } else {
            logger.info("添加用户失败！");
            return "Add failure";
        }
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(10)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String name = rootNode.findValue("name").asText();

        if (userService.deleteUser(name) > 0) {
            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 删除了一个用户：" + name);
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("删除用户成功");
            return "Delete success";
        } else {
            logger.info("删除用户失败");
            return "Delete failure";
        }
    }

    @PostMapping("/user/modify")
    public String modifyUser(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(12)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        User user = new User();
        user.setName(rootNode.findValue("name").asText());
        user.setPassword(rootNode.findValue("password").asText());

        if (userService.updateUser(user) > 0) {
            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + " 修改了一个用户：" + user.getName());
            log.setTime(logService.currentDate());
            logService.log(log);

            logger.info("修改用户成功");
            return "Modify success";
        } else {
            logger.info("修改用户失败");
            return "Modify failure";
        }
    }

    @PostMapping("/user/query")
    public Map<String, Object> queryUser(@RequestBody String params, HttpSession session) throws Exception {
        if (!((User) session.getAttribute("login")).hasRight(11)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String name = rootNode.findValue("name").asText();

        User user = userService.selectUser(name);
        if (user == null) {
            logger.info("未找到该用户");
            throw new Exception("Cannot find");
        }
        logger.info("已找到该用户");
        Map<String, Object> result = new HashMap<>();
        List<String> rights = rightService.selectRightByUser(name);
        List<Role> roles = new ArrayList<>();
        for (String role_name : rights) {
            roles.add(roleService.selectRole(role_name));
        }
        result.put("user", user);
        result.put("roles", roles);
        return result;
    }

    @PostMapping("/user/all")
    public Map<String, List> getAllUser(HttpSession session) throws Exception {
        if (!((User) session.getAttribute("login")).hasRight(11)) throw new Exception("Hasn't right");

        logger.info("查找所有用户");
        Map<String, List> result = new HashMap<>();
        result.put("result", userService.selectAll());
        return result;
    }

    @PostMapping("/getRoleFunction")
    public Map<String, Object> getRoleFunction(@RequestBody String params, HttpSession session) throws Exception {
        if (!((User) session.getAttribute("login")).hasRight(15)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String role_name = rootNode.findValue("name").asText();

        Role role = roleService.selectRole(role_name);
        if (role == null) {
            logger.info("未找到该角色");
            throw new Exception("Cannot find");
        }
        List<Integer> functions = role.getFunctionList();

        logger.info("查询角色的所有权限");
        Map<String, Object> result = new HashMap<>();
        result.put("description", role.getDescription());
        result.put("result", functionService.selectFunctionByList(functions));
        return result;
    }

    @PostMapping("/getUserRole")
    public Map<String, List> getUserRole(@RequestBody String params, HttpSession session) throws Exception {
        if (!((User) session.getAttribute("login")).hasRight(11)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String name = rootNode.findValue("name").asText();
        List<String> rights = rightService.selectRightByUser(name);

        List<Role> roles = new ArrayList<>();
        for (String role_name : rights) {
            roles.add(roleService.selectRole(role_name));
        }

        logger.info("查询用户的所有角色");
        Map<String, List> result = new HashMap<>();
        result.put("result", roles);
        return result;
    }

    @PostMapping("/assign/role")
    public String assignRole(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(21)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String user_name = rootNode.findValue("user_name").asText();

        List<String> roles = new ArrayList<>();
        //Array 数组版
        Iterator<JsonNode> ij = rootNode.findValue("roles").elements();
        while (ij.hasNext()) {
            roles.add(ij.next().asText());
        }

        List<Right> rights = new ArrayList<>();
        for (String role : roles) {
            Right right = new Right();
            right.setUser_name(user_name);
            right.setRole_name(role);
            right.setDescription("将" + role + "权限配置给" + user_name);
            rights.add(right);
        }
        rightService.updateRight(rights);

        Log log = new Log();
        log.setUser_name(u.getName());
        log.setContent(u.getName() + " 为用户配置了权限：" + user_name);
        log.setTime(logService.currentDate());
        logService.log(log);

        logger.info("分配角色成功");
        return "Assign success";
    }

    @PostMapping("/assign/user")
    public String assignUser(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(22)) throw new Exception("Hasn't right");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String con_name = rootNode.findValue("con_name").asText();
        for (int i = 1; i <= 3; i++) {
            List<String> users = new ArrayList<>();
            //Array 数组版
            for (JsonNode jsonNode : rootNode.findValue("value" + i)) {
                users.add(jsonNode.asText());
            }
            for (String user : users) {
                Processes process = new Processes();
                process.setCon_name(con_name);
                process.setUser_name(user);
                process.setType(i);
                process.setState(0);
                process.setTime(logService.currentDate());
                process.setContent("还没有进行任何操作");
                processService.insertProcess(process);
            }
        }

        State s = new State();
        s.setTime(logService.currentDate());
        s.setCon_name(con_name);
        s.setType(1);
        stateService.updateState(s);

        Log log = new Log();
        log.setUser_name(u.getName());
        log.setContent(u.getName() + " 为 " + con_name + " 合同分配了操作人员");
        log.setTime(logService.currentDate());
        logService.log(log);

        logger.info("分配操作员成功");
        return "Assign success";
    }

    @PostMapping("/getAssignUser")
    public Map<String, List> getAssignUser(HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(22)) throw new Exception("Hasn't right");

        List<Role> roles = roleService.selectAll();
        List<String> countersign = new ArrayList<>();
        List<String> audit = new ArrayList<>();
        List<String> sign = new ArrayList<>();
        for (Role r : roles) {
            if (r.getFunctionList().contains(5)) countersign.add(r.getName());
            if (r.getFunctionList().contains(6)) audit.add(r.getName());
            if (r.getFunctionList().contains(7)) sign.add(r.getName());
        }
        Set<String> result1 = new LinkedHashSet<>();
        Set<String> result2 = new LinkedHashSet<>();
        Set<String> result3 = new LinkedHashSet<>();
        for (String n : countersign) {
            result1.addAll(rightService.selectRightByRole(n));
        }
        for (String n : audit) {
            result2.addAll(rightService.selectRightByRole(n));
        }
        for (String n : sign) {
            result3.addAll(rightService.selectRightByRole(n));
        }

        List<User> r1 = new ArrayList<>();
        List<User> r2 = new ArrayList<>();
        List<User> r3 = new ArrayList<>();
        for(String n : result1) {
            r1.add(userService.selectUser(n));
        }
        for(String n : result2) {
            r2.add(userService.selectUser(n));
        }
        for(String n : result3) {
            r3.add(userService.selectUser(n));
        }

        logger.info("查询有权限操作合同的用户");
        Map<String, List> result = new HashMap<>();
        result.put("result1", r1);
        result.put("result2", r2);
        result.put("result3", r3);
        return result;
    }

    @PostMapping("/getAssignContract")
    public Map<String, List> getAssignContract(HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");
        if (!u.hasRight(22)) throw new Exception("Hasn't right");

        logger.info("查询待分配合同");
        Map<String, List> result = new HashMap<>();
        result.put("result", stateService.selectByType(0));
        return result;
    }

    @PostMapping("/log/all")
    public Map<String, List> getLog(HttpSession session) throws Exception {
        if (!((User) session.getAttribute("login")).hasRight(23)) throw new Exception("Hasn't right");

        logger.info("查询所有日志");
        Map<String, List> result = new HashMap<>();
        result.put("result", logService.getAllLog());
        return result;
    }
}
