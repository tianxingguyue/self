package com.job.core.base.dto;

public class PageDTO {
    //默认页码
    public static int DefaultPageNum = 1;

    //默认条数
    public static int DefaultPageSize = 10;


    private Integer pageNum;

    private Integer pageSize;


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
