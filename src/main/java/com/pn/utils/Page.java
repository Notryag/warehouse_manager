package com.pn.utils;

import lombok.Data;

import java.util.List;

@Data
public class Page {

    private  Integer pageNum;

    private Integer pageSize;

    private Integer totalNum;

    private Integer pageCount;

    private Integer limitIndex;

    private List<?> resultList;

    public Integer getPageCount() {
        return totalNum%pageSize==0 ? totalNum/pageSize : totalNum/pageSize+1;
    }

    public Integer getLimitIndex() {
        return pageSize * (pageNum-1);
    }
}
