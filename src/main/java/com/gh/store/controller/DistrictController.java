package com.gh.store.controller;

import com.gh.store.entity.District;
import com.gh.store.service.IDistrictService;
import com.gh.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController extends BaseController{
    @Autowired
    private IDistrictService districtService;
    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data=districtService.getByParent(parent);
        return new JsonResult<>(OK,data);
    }
}
