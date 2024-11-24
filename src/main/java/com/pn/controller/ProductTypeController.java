package com.pn.controller;

import com.pn.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/productCategory")
public class ProductTypeController {

    @GetMapping("/product-category-tree")
    public Result productCategoryTree() {

    }
}
