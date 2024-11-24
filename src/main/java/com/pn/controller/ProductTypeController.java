package com.pn.controller;

import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 校验分类编码是否已存在的url接口/productCategory/verify-type-code
     */
    @RequestMapping("/verify-type-code")
    public Result checkTypeCode(String typeCode){
        //执行业务
        Result result = productTypeService.queryTypeByCode(typeCode);
        //响应
        return result;
    }

    /**
     * 添加商品分类的url接口/productCategory/type-add
     *
     * @RequestBody ProductType productType将请求传递的json数据封装到参数ProductType对象;
     */
    @RequestMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType){
        //执行业务
        Result result = productTypeService.saveProductType(productType);
        //响应
        return result;
    }

    /**
     * 删除商品分类的url接口/productCategory/type-delete/{typeId}
     *
     * @PathVariable Integer typeId将路径占位符typeId的值赋值给参数变量typeId;
     */
    @RequestMapping("/type-delete/{typeId}")
    public Result deleteType(@PathVariable Integer typeId){
        //执行业务
        Result result = productTypeService.removeProductType(typeId);
        //响应
        return result;
    }

    /**
     * 修改商品分类的url接口/productCategory/type-update
     *
     * @RequestBody ProductType productType将请求传递的json数据封装到参数ProductType对象;
     */
    @RequestMapping("/type-update")
    public Result updateType(@RequestBody ProductType productType){
        //执行业务
        Result result = productTypeService.updateProductType(productType);
        //响应
        return result;
    }
}
