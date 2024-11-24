package com.pn.controller;

import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.service.StoreService;
import com.pn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;


    @GetMapping("/store-page-list")
    public Result storePageList(Page page, Store store) {
        page = storeService.queryStorePage(page, store);
        return Result.ok(page);
    }


    @GetMapping("/store-num-check")
    public Result storeNumCheck(String storeNum) {
        Result result = storeService.checkStoreSum(storeNum);
        return result;
    }

    @PostMapping("/store-add")
    public Result storeAdd(Store store) {
        Result result = storeService.saveStore(store);
        return result;
    }

    @PostMapping("/store-update")
    public Result storeUpdate(Store store) {
        Result result = storeService.updateStore(store);
        return result;
    }

    @PostMapping("/store-delete/{storeId}")
    public Result storeDelete(@PathVariable int storeId) {
        Result result = storeService.deleteStore(storeId);
        return result;
    }

    @GetMapping("/exportTable")
    public Result exportTable(Page page, Store store) {
        page = storeService.queryStorePage(page, store);
        List<?> resultList = page.getResultList();
        return Result.ok(resultList);
    }

}
