package com.gh.store.service;

import com.gh.store.entity.User;
import com.gh.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)

public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;
    @Test
    public void setDefault(){
       addressService.setDefault(9,104,"admin");
        System.out.println("ok");
    }
}
