package com.zhang.contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.Function;
import com.zhang.contract.entity.User;
import com.zhang.contract.service.FunctionService;
import com.zhang.contract.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private FunctionService functionService;

    @RequestMapping(value = {"login/name={name} password={password}"}, method = {RequestMethod.GET})
    public List<Function> login(@PathVariable String name, @PathVariable String password) {
        if (userService.login(name, password)) {
            User user = userService.selectUser(name);

            //等待修改
            List<Integer> idList = new ArrayList<>();
            idList.add(1);
            idList.add(3);
            return functionService.selectFunctionByList(idList);
        }
        return null;
    }

    @RequestMapping(value= {"selectAll"}, method={RequestMethod.GET})
    public List<User> selectAllUser() {
        logger.info("查询数据所有记录: ");
        List<User> result = userService.selectAll();
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"selectByID/{id}"}, method={RequestMethod.GET})
    public User selectUserByID(@PathVariable int id) {
        logger.info("查询数据ID为: " + id);
        User result = userService.selectUser(id);
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"selectByName/{name}"}, method={RequestMethod.GET})
    public User selectUserByName(@PathVariable String name) {
        logger.info("查询数据name为: " + name);
        User result = userService.selectUser(name);
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"delete/{name}"}, method={RequestMethod.GET})
    public int deleteUser(@PathVariable String name) {
        logger.info("删除数据name为: " + name);
        int result = userService.deleteUser(name);
        logger.info("删除数据成功");
        return result;
    }

    @RequestMapping(value= {"update"}, method={RequestMethod.POST})
    public int updateUser(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        User user = new User();
        user.setId(rootNode.path("id").asInt());
        user.setName(rootNode.path("name").asText());
        user.setPassword(rootNode.path("password").asText());
        logger.info("数据转为实体bean成功");
        int result = userService.updateUser(user);
        if (result != 0) {
            logger.info("数据修改成功");
        } else {
            logger.info("数据修改失败");
        }
        return result;
    }

    @RequestMapping(value= {"insert"}, method={RequestMethod.POST})
    public int insertUser(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        User user = new User();
        user.setName(rootNode.path("userName").asText());
        user.setPassword(rootNode.path("passWord").asText());
        logger.info("数据转为实体bean成功");
        int result = userService.insertUser(user);
        if (result != -1) {
            logger.info("数据入库成功");
        } else {
            logger.info("数据入库失败");
        }
        return result;
    }
}
