package com.pn.service;

import com.pn.entity.Product;
import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.mapper.ProductMapper;
import com.pn.mapper.ProductTypeMapper;
import com.pn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Value("${file.access-path}")
    private String accessPath;

    public Page queryProductPage(Page page, Product product) {
        int productCount = productMapper.selectProductCount(product);

        List<Product> products = productMapper.selectProductPage(page, product);
        page.setTotalNum(productCount);
        page.setResultList(products);
        return page;
    }

    public Result saveProduct(Product product) {
        product.setImgs(accessPath+product.getImgs());

        int result = productMapper.insertProduct(product);
        if (result == 1) {
            return Result.ok("添加成功");
        } else {
            return Result.err(Result.CODE_ERR_BUSINESS, "添加商品失败！");
        }
    }

    public Result updateProductState(Product product) {
        int result = productMapper.updateStateById(product);
        if (result == 1) {
            return Result.ok();
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");
    }


    public List<ProductType> allProductTypeTree() {
        List<ProductType> allTypeList = productTypeMapper.findAllProductType();

        List<ProductType> typeTreeList = allTypeToTypeTree(allTypeList, 0);
        return typeTreeList;
    }
    public Result deleteProduct(Integer productId) {
        //根据商品id删除商品
        int i = productMapper.deleteProductById(productId);
        if(i>0){
            return Result.ok("商品删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品删除失败！");
    }

    //修改商品的业务方法
    public Result updateProduct(Product product) {
        if(!product.getImgs().startsWith(accessPath)){
            product.setImgs(accessPath+product.getImgs());
        }
        //根据商品id修改商品信息
        int i = productMapper.updateProductById(product);
        if(i>0){
            return Result.ok("商品修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"商品修改失败！");
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
