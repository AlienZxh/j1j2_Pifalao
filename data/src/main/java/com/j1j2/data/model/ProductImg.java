package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-16.
 */
public class ProductImg {

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
}
