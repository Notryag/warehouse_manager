package com.pn.service;

import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.mapper.ProductMapper;
import com.pn.mapper.ProductTypeMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeService {


    private final ProductTypeMapper productTypeMapper;

    public ProductTypeService(ProductTypeMapper productTypeMapper) {
        this.productTypeMapper = productTypeMapper;
    }

    public List<ProductType> allProductTypeTree() {
        List<ProductType> allTypeList = productTypeMapper.findAllProductType();
        List<ProductType> typeTreeList = allTypeToTypeTree(allTypeList, 0);
        return typeTreeList;

    }

    public Result queryTypeByCode(String typeCode) {

        //根据分类编码查询商品分类
        ProductType productType = productTypeMapper.findTypeByCode(typeCode);

        return Result.ok(productType==null);
    }

    public Result saveProductType(ProductType productType) {
        //添加商品分类
        int i = productTypeMapper.insertProductType(productType);
        if(i>0){
            return Result.ok("分类添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "分类添加失败！");
    }

    public Result removeProductType(Integer typeId) {
        //根据分类id删除分类及其所有子级分类
        int i = productTypeMapper.deleteProductType(typeId);
        if(i>0){
            return Result.ok("分类删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "分类删除失败！");
    }

    public Result updateProductType(ProductType productType) {
        //根据分类id修改分类
        int i = productTypeMapper.updateTypeById(productType);
        if(i>0){
            return Result.ok("分类修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "分类修改失败！");
    }

    private List<ProductType> allTypeToTypeTree(List<ProductType> allTypeList, Integer parentId){

        List<ProductType> typeList = new ArrayList<>();
        for (ProductType productType : allTypeList) {
            if(productType.getParentId().equals(parentId)){
                typeList.add(productType);
            }
        }

        for (ProductType productType : typeList) {
            List<ProductType> childTypeList = allTypeToTypeTree(allTypeList, productType.getTypeId());
            productType.setChildProductCategory(childTypeList);
        }

        return typeList;
    }
}
