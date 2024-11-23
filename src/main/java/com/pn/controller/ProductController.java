package com.pn.controller;

import com.pn.entity.Brand;
import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.service.BrandService;
import com.pn.service.ProductService;
import com.pn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    private StoreService storeService;

    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    public ProductController(StoreService storeService) {
        this.storeService = storeService;
    }

    @RequestMapping("/store-list")
    public Result storeList() {
        List<Store> storeList = storeService.queryAllStore();
        return Result.ok(storeList);
    }

    @GetMapping("/brand-list")
    public Result brandList() {
        List<Brand> brands = brandService.queryAllBrand();
        return Result.ok(brands);
    }

    @GetMapping("/category-tree")
    public Result categoryTree() {
        List<ProductType> productTypes = productService.allProductTypeTree();
        return Result.ok(productTypes);
    }
}
