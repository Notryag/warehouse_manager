package com.pn.controller;

import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.service.InStoreService;
import com.pn.service.StoreService;
import com.pn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/instore")
public class InStoreController {

    @Autowired
    private InStoreService inStoreService;

    @Autowired
    private StoreService storeService;


    @GetMapping("/store-list")
    public Result storeList(){
        return Result.ok(storeService.queryAllStore());
    }

    @GetMapping("/instore-page-list")
    public Result inStorePageList(Page page, InStore inStore){
        page = inStoreService.queryInStorePage(page,inStore);
        return Result.ok(page);
    }

    @GetMapping("/instore-confrim")
    public Result confirmInStore(@RequestBody InStore inStore){
        return inStoreService.confirmInStore(inStore);
    }
}
