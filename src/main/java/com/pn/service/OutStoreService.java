package com.pn.service;

import com.pn.entity.OutStore;
import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.mapper.OutStoreMapper;
import com.pn.mapper.ProductMapper;
import com.pn.mapper.StoreMapper;
import com.pn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutStoreService {

    @Autowired
    private OutStoreMapper outStoreMapper;


    @Autowired
    private ProductMapper productMapper;


    public Result saveOutStore(OutStore outStore) {
        int i = outStoreMapper.insertOutStore(outStore);
        if (i > 0) {
            return Result.ok("添加成功");
        } else {
            return Result.err(Result.CODE_ERR_BUSINESS, "添加失败");
        }
    }

    public Page outStorePage(Page page, OutStore outStore) {
        int count = outStoreMapper.outStoreCount(outStore);
        page.setTotalNum(count);
        List<OutStore> outStoreList = outStoreMapper.outStorePage(page, outStore);
        page.setResultList(outStoreList);
        return page;
    }

    public Result confirmOutStore(OutStore outStore) {
        Product product = productMapper.selectProductById(outStore.getProductId());
        if (outStore.getOutNum()> product.getProductInvent()) {
            return Result.err(Result.CODE_ERR_BUSINESS, "库存不足");
        }
        int i = outStoreMapper.updateIsOutById(outStore.getOutsId());
        if (i > 0) {
            int j = productMapper.addInventById(outStore.getProductId(), -outStore.getOutNum());
            if (j>0) {
                return Result.ok("success");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "fail");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "fail");
    }

}
