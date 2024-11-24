package com.pn.service;

import com.pn.entity.ProductType;
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
