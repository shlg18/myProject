package com.gh.store.service;

import com.gh.store.query.CartQuery;
import com.gh.store.vo.CartVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/** 处理商品数据的业务层接口 */
public interface ICartService {
    /**
     * 将商品添加到购物车
     * @param uid 当前登录用户的id
     * @param pid 商品的id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 将购物车中某商品的数量加1
     * @param cid 购物车数量的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 将购物车中某商品的数量减1
     * @param cid 购物车数量的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 增加成功后新的数量
     */
    Integer reduceNum(Integer cid, Integer uid, String username);

    List<CartVO> getVOByCids(Integer[] cids, Integer uid);


    PageInfo<CartVO> getPageVOByUid(CartQuery cartQuery);
}
