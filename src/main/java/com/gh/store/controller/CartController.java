package com.gh.store.controller;

import com.gh.store.query.CartQuery;
import com.gh.store.service.ICartService;
import com.gh.store.util.JsonResult;
import com.gh.store.vo.CartVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController {
    @Autowired
    private ICartService cartService;
    @RequestMapping("/add_to_cart")
    public JsonResult addToCart(HttpSession session,Integer pid,Integer amount){
        cartService.addToCart(getUidFromSession(session),pid,amount,getUsernameFromSession(session));
        return new JsonResult(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session){
        List<CartVO> data = cartService.getVOByUid(getUidFromSession(session));
        return new JsonResult(OK,data);
    }

    @RequestMapping("/listAll")
    public JsonResult<PageInfo<CartVO>> findPageVOByUid(@ModelAttribute("cartQuery") CartQuery cartQuery, HttpSession session){
        cartQuery.setUid(getUidFromSession(session));
        PageInfo<CartVO> data = cartService.getPageVOByUid(cartQuery);
        return new JsonResult(OK,data);
    }
    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session){
        Integer data = cartService.addNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult(OK,data);
    }

    @RequestMapping("{cid}/num/reduce")
    public JsonResult<Integer> reduceNum(@PathVariable("cid") Integer cid, HttpSession session){
        Integer data = cartService.reduceNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult(OK,data);
    }

    @RequestMapping("list")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids,HttpSession session){
        List<CartVO> data=cartService.getVOByCids(cids,getUidFromSession(session));
        return new JsonResult<>(OK,data);
    }


}
