package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-3-16.
 */
public class ProductImg implements Parcelable {

    /**
     * ImgId : 201
     * MainId : 0
     * ImgUrl : http://data.j1j2.com/ResourceFiles/ProductImages/20140715/70_70/18208_0.jpg
     * Size : 1
     */

    private int ImgId;
    private int MainId;
    private String ImgUrl;
    private int Size;//1最小    3最大

    public void setImgId(int ImgId) {
        this.ImgId = ImgId;
    }

    public void setMainId(int MainId) {
        this.MainId = MainId;
    }

    public void setImgUrl(String ImgUrl) {
        this.ImgUrl = ImgUrl;
    }

    public void setSize(int Size) {
        this.Size = Size;
    }

    public int getImgId() {
        return ImgId;
    }

    public int getMainId() {
        return MainId;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public int getSize() {
        return Size;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ImgId);
        dest.writeInt(this.MainId);
        dest.writeString(this.ImgUrl);
        dest.writeInt(this.Size);
    }

    public ProductImg() {
    }

    protected ProductImg(Parcel in) {
        this.ImgId = in.readInt();
        this.MainId = in.readInt();
        this.ImgUrl = in.readString();
        this.Size = in.readInt();
    }

    public static final Parcelable.Creator<ProductImg> CREATOR = new Parcelable.Creator<ProductImg>() {
        @Override
        public ProductImg createFromParcel(Parcel source) {
            return new ProductImg(source);
        }

        @Override
        public ProductImg[] newArray(int size) {
            return new ProductImg[size];
        }
    };
}
