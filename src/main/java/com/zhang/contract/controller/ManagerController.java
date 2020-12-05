package com.zhang.contract.controller;

import com.zhang.contract.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public void setRoleForUser() {

    }

    public void createRole() {

    }

    public void deleteRole() {

    }

    public void modifyRole() {

    }

    public void queryRole() {

    }

    public void createCustomer() {

    }

    public void deleteCustomer() {

    }

    public void modifyCustomer() {

    }

    public void queryCustomer() {

    }

    public void createUser() {

    }

    public void deleteUser() {

    }

    public void modifyUser() {

    }

    public void queryUser() {

    }

}
