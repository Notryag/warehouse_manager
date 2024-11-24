package com.pn.controller;

import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/productCategory")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping("/product-category-tree")
    public Result productCategoryTree() {
        List<ProductType> allProductTypeTree = productTypeService.allProductTypeTree();
        return Result.ok(allProductTypeTree);
    }
}
