package com.gh.store.service.impl;

import com.gh.store.entity.Cart;
import com.gh.store.entity.Product;
import com.gh.store.mapper.CartMapper;
import com.gh.store.mapper.ProductMapper;
import com.gh.store.query.CartQuery;
import com.gh.store.service.ICartService;
import com.gh.store.service.ex.AccessDeniedException;
import com.gh.store.service.ex.CartNotFoundException;
import com.gh.store.service.ex.InsertException;
import com.gh.store.service.ex.UpdateException;
import com.gh.store.vo.CartVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date date=new Date();
        if(result==null){  //表示该用户并未将该商品添加到购物车
            Cart cart=new Cart();
            cart.setPid(pid);
            cart.setUserid(uid);
            cart.setNum(amount);
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());
            cart.setCreatedTime(date);
            cart.setCreatedUser(username);
            cart.setModifiedTime(date);
            cart.setModifiedUser(username);
            Integer row = cartMapper.insert(cart);
            if(row!=1){
                throw new InsertException("插入商品数据时出现未知错误，请联系系统管理员");
            }
        }else{  //表示该用户的购物车中已有该商品
            Integer num=result.getNum()+amount;
            Integer row = cartMapper.updateNumByCid(result.getCid(), num, username, date);
            if(row!=1){
                throw new UpdateException("修改商品数量时出现未知错误，请联系系统管理员");
            }

        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result=cartMapper.findByCid(cid);
        if(result==null){
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        if(!result.getUserid().equals(uid)){
            throw new AccessDeniedException("非法访问");
        }
        Integer num=result.getNum()+1;
        Integer row = cartMapper.updateNumByCid(cid, num, username, new Date());
        if(row!=1){
            throw new UpdateException("修改商品数量时出现未知错误，请联系系统管理员");
        }
        return num;
    }

    @Override
    public Integer reduceNum(Integer cid, Integer uid, String username) {
        Cart result=cartMapper.findByCid(cid);
        if(result==null){
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        if(!result.getUserid().equals(uid)){
            throw new AccessDeniedException("非法访问");
        }
        Integer num=result.getNum()-1;
        if(num<0){
            num= 0;
        }
        Integer row = cartMapper.updateNumByCid(cid, num, username, new Date());
        if(row!=1){
            throw new UpdateException("修改商品数量时出现未知错误，请联系系统管理员");
        }
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer[] cids, Integer uid) {
        List<CartVO> list = cartMapper.findVOByCids(cids);
        Iterator<CartVO> it=list.iterator();
        while(it.hasNext()){
            CartVO cartVO= it.next();
            if(!cartVO.getUserid().equals(uid)){
                it.remove();
            }
        }
        return list;
    }

    @Override
    public PageInfo<CartVO> getPageVOByUid(CartQuery cartQuery) {
        PageHelper.startPage(cartQuery.getPageNum(),cartQuery.getPageSize());
        List<CartVO> cartVOList = cartMapper.findPageVOByUid(cartQuery);
        Iterator<CartVO> it=cartVOList.iterator();
        while(it.hasNext()){
            CartVO cartVO= it.next();
            if(!cartVO.getUserid().equals(cartQuery.getUid())){
                it.remove();
            }
        }

        PageInfo<CartVO> pageInfo=new PageInfo<>(cartVOList);

        return pageInfo;
    }

}
