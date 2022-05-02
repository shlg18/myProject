package com.gh.store.service.impl;

import com.gh.store.entity.Address;
import com.gh.store.mapper.AddressMapper;
import com.gh.store.service.IAddressService;
import com.gh.store.service.IDistrictService;
import com.gh.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count=addressMapper.countByUid(uid);
        System.out.println(maxCount);
        if(count>=maxCount){
            throw new AddressCountLimitException("收货地址数量已经达到上限(" + maxCount + ")！");
        }
        address.setUserid(uid);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());
        Integer isDefault=count==0?1:0;
        address.setIsDefault(isDefault);

        String proviceName=districtService.getNameByCode(address.getProvinceCode());
        String cityName=districtService.getNameByCode(address.getCityCode());
        String areaName=districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(proviceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        Integer row=addressMapper.insert(address);
        if(row!=1){
            throw new InsertException("插入收货地址数据时出现未知错误，请联系系统管理员！");
        }
    }

    @Override
    @Cacheable(value="address", key= "#uid",condition = "#uid!=null")
    public List<Address> findByUid(Integer uid) {
        List<Address> list=addressMapper.findByUid(uid);
        for (Address address:list) {
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result=addressMapper.findByAid(aid);
        if(result==null){
            throw new AddressNotFoundException("收货地址不存在");
        }
        if(!result.getUserid().equals(uid)){
            throw new AccessDeniedException("非法访问数据");
        }
        Integer row=addressMapper.updateNonDefaultByUid(uid);
        if(row<1){
            throw new UpdateException("修改数据时产生的未知异常");
        }
        row=addressMapper.updateDefaultByAid(aid,username,new Date());
        if(row!=1){
            throw new UpdateException("修改数据时产生的未知异常");
        }
    }

    @Override
    @CacheEvict(value = "address",key="#aid+'-'+#uid+'-'+#username")
    public void delete(Integer aid, Integer uid, String username) {
        Address result=addressMapper.findByAid(aid);
        if(result==null){
            throw new AddressNotFoundException("收货地址不存在");
        }
        if(!result.getUserid().equals(uid)){
            throw new AccessDeniedException("非法访问数据");
        }
        Integer count=addressMapper.countByUid(uid);
        if(count==0){
            return;
        }
        if(result.getIsDefault()==0){
            return;
        }
        Integer row=addressMapper.deleteByAid(aid);
        if(row!=1){
            throw new DeleteException("删除收货地址产生的未知异常");
        }
        Address address=addressMapper.findLastModified(uid);
        row= addressMapper.updateDefaultByAid(address.getAid(),username,new Date());
        if(row!=1){
            throw new UpdateException("更新收货地址数据时出现未知错误，请联系系统管理员");
        }
    }
}
