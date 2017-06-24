package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-12-13.
 */

public class QueryProductParams {

    /// <summary>
    /// 查询页索引 从1开始
    /// </summary>
    private int PageIndex;

    /// <summary>
    /// 页数据大小
    /// </summary>
    private int PageSize;

    /// <summary>
    /// 模糊查询中的关键词
    /// </summary>
    private String Keys;

    /// <summary>
    /// 商品分类ID
    /// </summary>
    private Integer CategoryId;

    /// <summary>
    /// 排序类型
    /// 1: 按销量排序
    /// 2：按价格排序
    /// 3：按人气排序
    /// 0：默认的商品Ranking字段排序
    /// </summary>
    private int OrderByType;

    /// <summary>
    /// 选择的门店ID
    /// </summary>
    private int ShopId;

    /// <summary>
    /// 选择进入的服务ID
    /// </summary>
    private int ServiceId;

    /// <summary>
    /// 查询数据类型 ProductSearchType
    /// 0：只查询一般性的商品 不包括 促销商品和新品
    /// 1：只查询促销商品
    /// 2：只查询新品
    /// 3：查询所有商品类型
    /// 4：按主分类ID查询
    /// 5：按子分类ID查询
    /// 6：模糊查询
    /// </summary>
    private int QueryDataType;

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public String getKeys() {
        return Keys;
    }

    public void setKeys(String keys) {
        Keys = keys;
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public int getOrderByType() {
        return OrderByType;
    }

    public void setOrderByType(int orderByType) {
        OrderByType = orderByType;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
        ServiceId = serviceId;
    }

    public int getQueryDataType() {
        return QueryDataType;
    }

    public void setQueryDataType(int queryDataType) {
        QueryDataType = queryDataType;
    }
}
