package com.gh.store.mapper;

import com.gh.store.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/** 处理收货地址数据的持久层接口 */
@Repository
public interface AddressMapper {
    /**
     * 插入收货地址数据
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 统计某用户的收货地址数据的数量
     * @param uid 用户的id
     * @return 该用户的收货地址数据的数量
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户uid查询收货地址
     * @param uid 用户id
     * @return 该用户的收货地址数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据地址的aid查询收货地址数据
     * @param aid 地址的aid
     * @return 收货地址数据
     */
    Address findByAid(Integer aid);

    /**
     * 根据uid设置该用户的所有收货地址为非默认
     * @param uid 用户的id
     * @return 受影响的行数
     */
    Integer updateNonDefaultByUid(Integer uid);

    /**
     * 设置指定收货地址的aid为默认收货地址
     * @param aid 收货地址aid
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(@Param("aid") Integer aid, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime")Date modifiedTime);

    /**
     * 根据收货地址id删除数据
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据用户uid查询最新修改的收货地址数据
     * @param uid 用户uid
     * @return 该用户最后修改的收货地址，如果该用户没有收货地址数据则返回null
     */
    Address findLastModified(Integer uid);

}
