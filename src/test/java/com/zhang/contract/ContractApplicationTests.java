package com.zhang.contract;

import com.zhang.contract.entity.Contract;
import com.zhang.contract.service.ContractService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContractApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(ContractApplicationTests.class);
    @Autowired
    ContractService contractService;

    @Test
    void contextLoads() {
        Contract contract = new Contract();
        contract.setName("B");
        contract.setUser_name("super");
        contract.setCustomer_name("A");
        contract.setContent("A");
        if (contractService.insertContract(contract) != -1) {
            logger.info("新建合同成功");
            logger.info(contract.getId() + "");
            //stateService.insertState(contract.);

            //return "Draft success";
        }
        //contractService.insertContract();
    }
}
