package com.pn.controller;

import com.pn.entity.OutStore;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.service.StoreService;
import com.pn.utils.CurrentUser;
import com.pn.utils.Page;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.pn.service.OutStoreService;

import java.util.List;

@RestController
@RequestMapping("/outstore")
public class OutStoreController {

    @Autowired
    private OutStoreService outStoreService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private StoreService storeService;


    @GetMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore,
                              @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        int userId = currentUser.getUserId();

        outStore.setCreateBy(userId);

        Result result = outStoreService.saveOutStore(outStore);
        return result;
    }

    @GetMapping("/store-list")
    public Result storeList() {
        List<Store> stores = storeService.queryAllStore();
        return Result.ok(stores);
    }

    @GetMapping("/outstore-page-list")
    public Result outStorePageList(Page page, OutStore outStore) {
        page = outStoreService.outStorePage(page, outStore);
        return Result.ok(page);
    }

    @GetMapping("/outstore-confrim")
    public Result comrirmOutStore(@RequestBody OutStore outStore) {
        Result result = outStoreService.confirmOutStore(outStore);
        return result;
    }

}
