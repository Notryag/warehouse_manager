package com.pn.mapper;

import com.pn.entity.OutStore;
import com.pn.utils.Page;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OutStoreMapper {
    //添加出库单的方法
    public int insertOutStore(OutStore outStore);

    //查询出库单总行数的方法
    public int outStoreCount(OutStore outStore);

    //分页查询出库单的方法
    public List<OutStore> outStorePage(@Param("page") Page page, @Param("outStore") OutStore outStore);

    //根据id将出库单状态改为已出库的方法
    public int updateIsOutById(Integer outsId);
}
