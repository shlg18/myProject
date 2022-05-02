package com.gh.store.service.impl;

import com.gh.store.entity.Address;
import com.gh.store.entity.District;
import com.gh.store.mapper.AddressMapper;
import com.gh.store.mapper.DistrictMapper;
import com.gh.store.service.IAddressService;
import com.gh.store.service.IDistrictService;
import com.gh.store.service.ex.AddressCountLimitException;
import com.gh.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        List<District> list=districtMapper.findByParent(parent);
        for (District d:list) {
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {

        return districtMapper.findNameByCode(code);
    }
}
