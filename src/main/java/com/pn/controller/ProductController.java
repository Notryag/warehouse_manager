package com.pn.controller;

import com.pn.dto.Unit;
import com.pn.entity.*;
import com.pn.service.*;
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

    @Autowired
    private SupplyService supplyService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private UnitService unitService;

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

    @GetMapping("/supply-list")
    public Result supplyList() {
        List<Supply> supplies = supplyService.queryAllSupply();
        return Result.ok(supplies);
    }

    @GetMapping("/place-list")
    public Result PlaceList() {
        List<Place> places = placeService.queryAllPlace();
        return Result.ok(places);
    }

    @GetMapping("/unit-list")
    public Result unitList() {
        List<Unit> units = unitService.queryAllUnit();
        return Result.ok(units);
    }


}
