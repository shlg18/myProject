package com.gh.store.mapper;
import com.gh.store.entity.Address;
import com.gh.store.entity.User;
import com.gh.store.service.IAddressService;
import com.gh.store.service.IProductService;
import com.gh.store.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Test
    public void testInsert(){
        Address address=new Address();
        address.setUserid(6);
        address.setName("Zhangsan");
        address.setPhone("15200011110");
        Integer row=addressMapper.insert(address);
        System.out.println(row);
    }
    @Test
    public void testcountByUid(){
        System.out.println(addressMapper.countByUid(104));
    }
    @Test
    public void testAdd(){
        Address address=new Address();
        address.setPhone("188666000");
        address.setName("朋友");
        addressService.addNewAddress(7,"系统管理员",address);
    }
    @Test
    public void testfindByUid(){
        Address address=addressService.findByUid(104).get(0);
        System.out.println(address);
    }

    @Test
    public void testfindByAid(){
        System.out.println(addressMapper.findByAid(9));
    }
    @Test
    public void testupdateNonDefaultByUid(){
        System.out.println(addressMapper.updateNonDefaultByUid(104));
    }

    @Test
    public void testupdateDefaultByAid(){
        System.out.println(addressMapper.updateDefaultByAid(7,"admin",new Date()));
    }

    @Test
    public void testdelete(){
        addressService.delete(4,7,"admin");
        System.out.println("ok");
    }
    @Test
    public void testfindHot(){
        System.out.println(productMapper.findHotList());
    }

}

