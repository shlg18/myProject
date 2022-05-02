package com.gh.store.service;

import com.gh.store.entity.Address;

import java.util.List;

/** 处理收货地址数据的业务层接口 */
public interface IAddressService {

    void addNewAddress(Integer uid, String username, Address address);

    /**
     * 查询某用户的收货地址列表数据
     * @param uid 收货地址归属的用户id
     * @return 该用户的收货地址列表数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 设置某个用户某条收货地址为默认
     * @param aid 收货地址aid
     * @param uid 用户uid
     * @param username 修改的执行人
     */
    void setDefault(Integer aid,Integer uid,String username);

    /**
     * 删除收货地址
     * @param aid 收货地址id
     * @param uid 归属的用户id
     * @param username 当前登录的用户名
     */
    void delete(Integer aid,Integer uid,String username);
}
