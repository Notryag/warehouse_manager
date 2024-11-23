package com.pn.service;

import com.pn.entity.Brand;
import com.pn.mapper.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public List<Brand> queryAllBrand() {
        return brandMapper.findAllBrand();
    }
}
