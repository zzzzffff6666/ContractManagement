package com.zhang.contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.Log;
import com.zhang.contract.entity.User;
import com.zhang.contract.service.LogService;
import com.zhang.contract.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ConnectController {
    private static final Logger logger = LoggerFactory.getLogger(ConnectController.class);

    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody String params, HttpSession session) throws IOException {
        Map<String, Object> result = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        String name = rootNode.path("name").asText();
        String password = rootNode.path("password").asText();

        logger.info("用户登录：name={}, password={}", name, password);

        User user = userService.selectUser(name);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                logger.info("登陆成功！");

                user.setFunctions(userService.getRight(name));

                //将内容保存在Session中
                session.setAttribute("login", user);
                result.put("result", user);
            } else {
                logger.info("登陆失败！密码错误！");
                result.put("result", "Error password");
            }
        } else {
            logger.info("登陆失败！未找到该用户！");
            result.put("result", "Error name");
        }
        return result;
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String register(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        User user = new User();
        user.setName(rootNode.findValue("name").asText());
        user.setPassword(rootNode.findValue("password").asText());

        logger.info("用户注册：name={}, password={}", user.getName(), user.getPassword());

        if (userService.insertUser(user) != -1) {
            Log log = new Log();
            log.setUser_name(user.getName());
            log.setContent(user.getName() + "注册成功");
            log.setTime(logService.currentDate());
            logService.log(log);
            //注册成功之后的操作
            logger.info("注册成功！");
            return "Register success";
        } else {
            logger.info("注册失败！");
            return "Register failure";
        }
    }

    @PostMapping("/modifyPassword")
    public String modifyUser(@RequestBody String params, HttpSession session) throws Exception {
        User u = (User) session.getAttribute("login");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);

        String password = rootNode.findValue("old_password").asText();
        String new_password = rootNode.findValue("new_password").asText();
        if (!password.equals(u.getPassword())) {
            logger.info("密码不匹配");
            return "Error password";
        }

        u.setPassword(new_password);
        if (userService.updateUser(u) > 0) {

            Log log = new Log();
            log.setUser_name(u.getName());
            log.setContent(u.getName() + "修改了一个密码");
            log.setTime(logService.currentDate());
            logService.log(log);

            session.setAttribute("login", null);
            logger.info("修改密码成功");
            return "Modify success";
        } else {
            logger.info("修改密码失败");
            return "Modify failure";
        }
    }

    @PostMapping("/exit")
    public String exitLogin(HttpSession session) throws Exception {
        session.setAttribute("login", null);
        logger.info("退出登录");
        return "Exit";
    }
}
