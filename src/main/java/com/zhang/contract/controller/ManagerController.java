package com.zhang.contract.controller;

import com.zhang.contract.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
public class ManagerController {

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

}
