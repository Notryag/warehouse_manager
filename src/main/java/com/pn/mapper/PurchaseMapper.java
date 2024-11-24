package com.pn.mapper;

import com.pn.entity.Purchase;
import com.pn.utils.Page;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseMapper {
    //添加采购单的方法
    public int insertPurchase(Purchase purchase);

    //查询采购单总行数的方法
    public int selectPurchaseCount(Purchase purchase);

    //分页查询采购单的方法
    public List<Purchase> selectPurchasePage(@Param("page") Page page, @Param("purchase") Purchase purchase);

    //根据id修改采购单的方法
    public int updatePurchaseById(Purchase purchase);

    //根据id删除采购单的方法
    public int deletePurchaseById(Integer buyId);

    public int updateIsInById(Integer buyId);

}
