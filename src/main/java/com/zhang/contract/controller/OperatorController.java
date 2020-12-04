package com.zhang.contract.controller;

import com.zhang.contract.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operate")
public class OperatorController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private StateService stateService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;


}
