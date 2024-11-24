package com.pn.service;

import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.mapper.InStoreMapper;
import com.pn.mapper.ProductMapper;
import com.pn.mapper.PurchaseMapper;
import com.pn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InStoreService {
    @Autowired
    private InStoreMapper inStoreMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PurchaseMapper purchaseMapper;


    public Result saveInStore(InStore inStore, Integer buyId) {
        //添加入库单
        int i = inStoreMapper.insertInStore(inStore);
        if(i>0){
            //根据id将采购单状态改为已入库
            int j = purchaseMapper.updateIsInById(buyId);
            if(j>0){
                return Result.ok("入库单添加成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
    }

    public Page queryInStorePage(Page page, InStore inStore) {
        int inStoreCount = inStoreMapper.selectInStoreCount(inStore);
        List<InStore> inStores = inStoreMapper.selectInStorePage(page, inStore);

        page.setTotalNum(inStoreCount);
        page.setResultList(inStores);
        return page;
    }

    public Result confirmInStore(InStore inStore) {
        int i = inStoreMapper.updateIsInById(inStore.getInsId());
        if (i >0 ) {
            int j = productMapper.addInventById(inStore.getProductId(), inStore.getInNum());
            if (j > 0) {
                return Result.ok("success");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "fail");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "fail");
    }
}
