package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alienzxh on 16-9-14.
 */
public class ActivityWinProductHistoryInfo implements Parcelable {
    /// <summary>
    /// 商品ID
    /// </summary>
    private int ProductId;

    /// <summary>
    /// 活动商品名称
    /// </summary>
    private String ProductName;

    /// <summary>
    /// 商品图片
    /// </summary>
    private List<ActivityProductImg> Imgs;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public List<ActivityProductImg> getImgs() {
        return Imgs;
    }

    public void setImgs(List<ActivityProductImg> imgs) {
        Imgs = imgs;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ProductId);
        dest.writeString(this.ProductName);
        dest.writeTypedList(this.Imgs);
    }

    public ActivityWinProductHistoryInfo() {
    }

    protected ActivityWinProductHistoryInfo(Parcel in) {
        this.ProductId = in.readInt();
        this.ProductName = in.readString();
        this.Imgs = in.createTypedArrayList(ActivityProductImg.CREATOR);
    }

    public static final Parcelable.Creator<ActivityWinProductHistoryInfo> CREATOR = new Parcelable.Creator<ActivityWinProductHistoryInfo>() {
        @Override
        public ActivityWinProductHistoryInfo createFromParcel(Parcel source) {
            return new ActivityWinProductHistoryInfo(source);
        }

        @Override
        public ActivityWinProductHistoryInfo[] newArray(int size) {
            return new ActivityWinProductHistoryInfo[size];
        }
    };
}
