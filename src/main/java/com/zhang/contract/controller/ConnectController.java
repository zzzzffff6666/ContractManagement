package com.zhang.contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.Function;
import com.zhang.contract.entity.Right;
import com.zhang.contract.entity.User;
import com.zhang.contract.service.FunctionService;
import com.zhang.contract.service.RightService;
import com.zhang.contract.service.RoleService;
import com.zhang.contract.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/connect")
public class ConnectController {

    @Autowired
    private UserService userService;
    @Autowired
    private FunctionService functionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RightService rightService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String name = rootNode.path("name").asText();
        String password = rootNode.path("password").asText();
        if (userService.login(name, password)) {
            //登录成功之后的操作
            User user = userService.selectUser(name);
            List<Right> list = rightService.selectRight(name);
            List<Integer> rList = new ArrayList<>();
            for (Right r : list) {
                rList.addAll(roleService.selectRole(r.getRole_name()).getFunctionList());
            }
            List<Function> fList = functionService.selectFunctionByList(rList);

            return "Login Success";
        } else return "Login Failure";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String register(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        User user = new User();
        user.setName(rootNode.path("name").asText());
        user.setPassword(rootNode.path("password").asText());
        if (userService.insertUser(user) != -1) {
            //注册成功之后的操作

            return "Register Success";
        } else return "Register Failure";
    }
}
