package com.j1j2.data.model;

import java.util.List;

/**
 * Created by alienzxh on 16-3-15.
 */
public class PagerManager<T> {



    private int PageLength;
    private int PageIndex;
    private int Offset;
    private int PageSize;
    private int TotalCount;
    private int PageCount;


    private java.util.List<T> List;

    public int getPageLength() {
        return PageLength;
    }

    public void setPageLength(int pageLength) {
        PageLength = pageLength;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getOffset() {
        return Offset;
    }

    public void setOffset(int offset) {
        Offset = offset;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public java.util.List<T> getList() {
        return List;
    }

    public void setList(java.util.List<T> list) {
        List = list;
    }
}
