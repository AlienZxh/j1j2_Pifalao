package com.j1j2.data.model;

import java.util.List;

/**
 * Created by alienzxh on 16-3-14.
 */
public class SecondarySort {


    /**
     * SortId : 1553
     * SortName : 休闲食品
     * FoodSortRanking : 1
     * Visible : true
     * ParentSortId : null
     * ProductCount : 832
     * WebImgPath : /ResourceFiles/ProductSortImg/Web/1.jpg
     * AppImgPath : /ResourceFiles/ProductSortImg/App/1.jpg
     * IsHot : false
     * SortDescription : null
     * ModuleId : 0
     */

    private ProductSort ParentProductSort;
    /**
     * SortId : 1555
     * SortName : 饼干
     * FoodSortRanking : 4
     * Visible : true
     * ParentSortId : 1553
     * ProductCount : 152
     * WebImgPath : /ResourceFiles/ProductSortImg/Web/3.jpg
     * AppImgPath : /ResourceFiles/ProductSortImg/App/3.jpg
     * IsHot : false
     * SortDescription : 充饥必备干粮
     * ModuleId : 0
     */

    private List<ProductSort> ChildFoodSorts;

    public ProductSort getParentProductSort() {
        return ParentProductSort;
    }

    public void setParentProductSort(ProductSort parentProductSort) {
        ParentProductSort = parentProductSort;
    }

    public List<ProductSort> getChildFoodSorts() {
        return ChildFoodSorts;
    }

    public void setChildFoodSorts(List<ProductSort> childFoodSorts) {
        ChildFoodSorts = childFoodSorts;
    }

    @Override
    public String toString() {
        return "SecondarySort{" +
                "ParentProductSort=" + ParentProductSort +
                ", ChildFoodSorts=" + ChildFoodSorts +
                '}';
    }
}
