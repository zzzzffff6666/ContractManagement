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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/connect")
public class ConnectController {
    private static final Logger logger = LoggerFactory.getLogger(ConnectController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private FunctionService functionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RightService rightService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public User login(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String name = rootNode.path("name").asText();
        String password = rootNode.path("password").asText();

        logger.info("用户登录：name={}, password={}", name, password);

        if (userService.login(name, password)) {

            logger.info("登陆成功！");

            //登录成功之后的操作
            User user = userService.selectUser(name);
            List<Right> list = rightService.selectRight(name);
            List<Integer> rList = new ArrayList<>();
            for (Right r : list) {
                rList.addAll(roleService.selectRole(r.getRole_name()).getFunctionList());
            }
            List<Function> fList = functionService.selectFunctionByList(rList);
            user.setFunctions(fList);

            return user;
        } else {
            logger.info("登陆失败！");
            return null;
        }
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String register(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        User user = new User();
        user.setName(rootNode.path("name").asText());
        user.setPassword(rootNode.path("password").asText());

        logger.info("用户注册：name={}, password={}", user.getName(), user.getPassword());

        if (userService.insertUser(user) != -1) {
            //注册成功之后的操作
            logger.info("注册成功！");
            return "Register Success";
        } else {
            logger.info("注册失败！");
            return "Register Failure";
        }
    }
}
