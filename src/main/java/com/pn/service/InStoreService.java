package com.pn.service;

import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.mapper.InStoreMapper;
import com.pn.mapper.ProductMapper;
import com.pn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InStoreService {

    private InStoreMapper inStoreMapper;

    @Autowired
    private InStoreService inStoreService;
    @Autowired
    private ProductMapper productMapper;

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
