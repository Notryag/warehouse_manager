package com.pn.service;

import com.pn.entity.Purchase;
import com.pn.entity.Result;
import com.pn.mapper.PurchaseMapper;
import com.pn.utils.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseMapper purchaseMapper;

    public PurchaseService(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    public Result savePurchase(Purchase purchase) {
        int i = purchaseMapper.insertPurchase(purchase);
        if (i > 0) {
            return Result.ok("采购单添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单添加失败！");
    }

    public Page queryPurchasePage(Page page, Purchase purchase) {
        int purchaseCount = purchaseMapper.selectPurchaseCount(purchase);

        List<Purchase> purchases = purchaseMapper.selectPurchasePage(page, purchase);
        page.setTotalNum(purchaseCount);
        page.setResultList(purchases);
        return page;
    }

    public Result updatePurchase(Purchase purchase) {
        int i = purchaseMapper.updatePurchaseById(purchase);
        if (i > 0) {
            return Result.ok("修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");
    }

    public Result deletePurchase(Integer buyId) {
        int i = purchaseMapper.deletePurchaseById(buyId);
        if (i > 0) {
            return Result.ok();
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "删除失败！");
    }
}
