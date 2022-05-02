package com.gh.store.controller;

import com.gh.store.entity.Product;
import com.gh.store.service.IProductService;
import com.gh.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.Integer;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController extends BaseController{
    @Autowired
    private IProductService productService;
    @RequestMapping("/hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> data = productService.findHotList();
        return new JsonResult<List<Product>>(OK, data);
    }

    @GetMapping("{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id) {
        // 调用业务对象执行获取数据
        Product data = productService.findById(id);
        // 返回成功和数据

        return new JsonResult<Product>(OK, data);
    }
}
