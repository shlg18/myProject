package com.gh.store.mapper;
import com.gh.store.entity.Address;
import com.gh.store.entity.Cart;
import com.gh.store.service.IAddressService;
import com.gh.store.service.ICartService;
import com.gh.store.service.IProductService;
import com.gh.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ICartService cartService;

    @Test
    public void insert(){
        Cart cart=new Cart();
        cart.setUserid(104);
        cart.setPid(10000014);
        cart.setNum(2);
        cart.setPrice(1000L);
        Integer insert = cartMapper.insert(cart);
        System.out.println(insert);
    }

    @Test
    public void updateNumByCid(){
        Integer marray = cartMapper.updateNumByCid(3, 4, "Marray", new Date());

        System.out.println(marray);
    }
    @Test
    public void findByUidAndPid(){
        Cart cart = cartMapper.findByUidAndPid(104, 10000014);
        System.out.println(cart);
    }

    @Test
    public void addToCart(){
        cartService.addToCart(3,10000001,5,"admin");
    }

    @Test
    public void getVOByUid(){
        System.out.println(cartService.getVOByUid(104));
    }

    @Test
    public void findByCid(){
        System.out.println(cartMapper.findByCid(4));
    }

    @Test
    public void addNum(){
        Integer num = cartService.addNum(4, 104, "admin");
        System.out.println(num);
    }

    @Test
    public void findVOByCids(){
        Integer[] cids=new Integer[]{3,4,5};
        List<CartVO> cartVOS = cartMapper.findVOByCids(cids);
        System.out.println(cartVOS);
    }
}

