package com.zhang.contract.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.contract.entity.Role;
import com.zhang.contract.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping(value= {"selectAll"}, method={RequestMethod.GET})
    public List<Role> selectAllRole() {
        logger.info("查询数据所有记录: ");
        List<Role> result = roleService.selectAll();
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"selectByID/{id}"}, method={RequestMethod.GET})
    public String selectRoleByID(@PathVariable int id) {
        logger.info("查询数据ID为: " + id);
        String result = roleService.selectRole(id);
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"selectByName/{name}"}, method={RequestMethod.GET})
    public String selectRoleByName(@PathVariable String name) {
        logger.info("查询数据name为: " + name);
        String result = roleService.selectRole(name);
        logger.info("查询数据成功");
        return result;
    }

    @RequestMapping(value= {"delete/{name}"}, method={RequestMethod.GET})
    public int deleteRole(@PathVariable String name) {
        logger.info("删除数据name为: " + name);
        int result = roleService.deleteRole(name);
        logger.info("删除数据成功");
        return result;
    }

    @RequestMapping(value= {"update"}, method={RequestMethod.POST})
    public String updateRole(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Role role = new Role();
        role.setId(rootNode.path("id").asInt());
        role.setName(rootNode.path("name").asText());
        role.setDescription(rootNode.path("description").asText());
        role.setFunctions(rootNode.path("functions").asText());
        logger.info("数据转为实体bean成功");
        int result = roleService.updateRole(role);
        if (result != 0) {
            logger.info("数据修改成功");
            return "Commit Success";
        } else {
            logger.info("数据修改失败");
            return "Commit Fail";
        }
    }

    @RequestMapping(value= {"insert"}, method={RequestMethod.POST})
    public String insertRole(@RequestBody String params) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(params);
        logger.info("解析数据成功");
        Role role = new Role();
        role.setId(rootNode.path("id").asInt());
        role.setName(rootNode.path("name").asText());
        role.setDescription(rootNode.path("description").asText());
        role.setFunctions(rootNode.path("functions").asText());
        logger.info("数据转为实体bean成功");
        int result = roleService.insertRole(role);
        if (result != 0) {
            logger.info("数据入库成功");
            return "Commit Success";
        } else {
            logger.info("数据入库失败");
            return "Commit Fail";
        }
    }
}
