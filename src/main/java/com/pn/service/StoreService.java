package com.pn.service;

import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.mapper.StoreMapper;
import com.pn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    public StoreMapper storeMapper;

    public List<Store> queryAllStore() {
        return storeMapper.findAllStore();
    }

    public Page queryStorePage(Page page, Store store) {
        int storeCount = storeMapper.selectStoreCount(store);

        List<Store> storeList = storeMapper.selectStorePage(page, store);

        page.setTotalNum(storeCount);
        page.setResultList(storeList);
        return page;
    }

    //校验仓库编号是否已存在的业务方法
    public Result checkStoreSum(String storeNum) {
        Store store = storeMapper.selectStoreByNum(storeNum);
        return Result.ok(store == null);
    }

    public Result saveStore(Store store) {
        int i = storeMapper.insertStore(store);
        if (i > 0) {
            return Result.ok();
        }
        return  Result.err(Result.CODE_ERR_BUSINESS, "add store fail");
    }

    public Result updateStore(Store store) {
        int i = storeMapper.updateStoreById(store);
        if (i > 0) {
            return Result.ok();
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "update store fail");
    }

    public Result deleteStore(Integer storeId) {
        int i = storeMapper.deleteStoreById(storeId);
        if (i > 0) {
            return Result.ok();
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "delete store fail");
    }
}
