package com.pn.controller;

import com.pn.entity.*;
import com.pn.service.InStoreService;
import com.pn.service.PurchaseService;
import com.pn.service.StoreService;
import com.pn.utils.CurrentUser;
import com.pn.utils.Page;
import com.pn.utils.WarehouseConstants;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.pn.utils.TokenUtils;

import java.util.List;

@RestController("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private InStoreService inStoreService;
    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/purchase-add")
    public Result addPurchase(@RequestBody Purchase purchase) {
        Result result = purchaseService.savePurchase(purchase);
        return result;
    }

    @GetMapping("/store-list")
    public Result storeList() {
        List<Store> stores = storeService.queryAllStore();
        return Result.ok(stores);
    }

    @GetMapping("/purchase-page-list")
    public Result purchasePageList(Page page, Purchase purchase) {
        page = purchaseService.queryPurchasePage(page, purchase);
        return Result.ok(page);
    }

    @PostMapping("/purchase-update")
    public Result updatePurchase(@RequestBody Purchase purchase) {
        Result result = purchaseService.updatePurchase(purchase);
        return result;
    }

    @GetMapping("/purchase-delete/{buyId}")
    public Result deletePurchase(@PathVariable Integer buyId) {
        Result result = purchaseService.deletePurchase(buyId);
        return result;
    }

    @GetMapping("/in-warehouse-record-add")
    public Result addInStore(@RequestBody Purchase purchase,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        int userId = currentUser.getUserId();
        InStore inStore = new InStore();
        inStore.setStoreId(purchase.getStoreId());
        inStore.setProductId(purchase.getProductId());
        inStore.setInNum(purchase.getBuyNum());
        inStore.setCreateBy(currentUser.getUserId());

        Result result = inStoreService.saveInStore(inStore, purchase.getBuyId());
        return result;
    }

}
