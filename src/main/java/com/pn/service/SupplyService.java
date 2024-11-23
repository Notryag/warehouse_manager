package com.pn.service;

import com.pn.entity.Supply;
import com.pn.mapper.SupplyMapper;
import com.pn.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@Service
public class SupplyService  {

    //注入SupplyMapper
    @Autowired
    private SupplyMapper supplyMapper;


    public List<Supply> queryAllSupply() {
        //查询所有供应商
        return supplyMapper.findAllSupply();
    }
}
