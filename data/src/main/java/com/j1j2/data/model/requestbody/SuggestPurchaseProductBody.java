package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class SuggestPurchaseProductBody {

    private String ProductName;// 建议采购商品名称

    private String Suggestion;// 建议其它内容

    private int UserId;// 记录提交人，可空

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSuggestion() {
        return Suggestion;
    }

    public void setSuggestion(String suggestion) {
        Suggestion = suggestion;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    @Override
    public String toString() {
        return "SuggestPurchaseProductBody{" +
                "ProductName='" + ProductName + '\'' +
                ", Suggestion='" + Suggestion + '\'' +
                ", UserId=" + UserId +
                '}';
    }
}
