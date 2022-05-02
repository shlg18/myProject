package com.gh.store.controller;

import com.gh.store.entity.Address;
import com.gh.store.service.IAddressService;
import com.gh.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController {
    @Autowired
    private IAddressService addressService;
    @RequestMapping("/add_new_address")
    public JsonResult addNewAddress(HttpSession session, Address address){
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        return new JsonResult(OK);
    }
    @RequestMapping({"","/"})
    public JsonResult<List<Address>>getByUid(HttpSession session){
        Integer uid=getUidFromSession(session);
        List<Address> data=addressService.findByUid(uid);
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("{aid}/set_default")
    public JsonResult setDefault(@PathVariable("aid") Integer id, HttpSession session){
        addressService.setDefault(id,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult(OK);
    }
    @RequestMapping("{aid}/delete")
    public JsonResult delete(HttpSession session,@PathVariable("aid") Integer id){
        addressService.delete(id,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult(OK);
    }
}
