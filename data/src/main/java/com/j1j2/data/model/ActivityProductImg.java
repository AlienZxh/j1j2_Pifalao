package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-9-7.
 */
public class ActivityProductImg implements Parcelable {
    private int ImgId;
    private int ActivityProductId;
    private String Normal;
    private String S640X640;
    private String S320X320;
    private String S100X100;

    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int imgId) {
        ImgId = imgId;
    }

    public int getActivityProductId() {
        return ActivityProductId;
    }

    public void setActivityProductId(int activityProductId) {
        ActivityProductId = activityProductId;
    }

    public String getNormal() {
        return Normal;
    }

    public void setNormal(String normal) {
        Normal = normal;
    }

    public String getS640X640() {
        return S640X640;
    }

    public void setS640X640(String s640X640) {
        S640X640 = s640X640;
    }

    public String getS320X320() {
        return S320X320;
    }

    public void setS320X320(String s320X320) {
        S320X320 = s320X320;
    }

    public String getS100X100() {
        return S100X100;
    }

    public void setS100X100(String s100X100) {
        S100X100 = s100X100;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ImgId);
        dest.writeInt(this.ActivityProductId);
        dest.writeString(this.Normal);
        dest.writeString(this.S640X640);
        dest.writeString(this.S320X320);
        dest.writeString(this.S100X100);
    }

    public ActivityProductImg() {
    }

    protected ActivityProductImg(Parcel in) {
        this.ImgId = in.readInt();
        this.ActivityProductId = in.readInt();
        this.Normal = in.readString();
        this.S640X640 = in.readString();
        this.S320X320 = in.readString();
        this.S100X100 = in.readString();
    }

    public static final Parcelable.Creator<ActivityProductImg> CREATOR = new Parcelable.Creator<ActivityProductImg>() {
        @Override
        public ActivityProductImg createFromParcel(Parcel source) {
            return new ActivityProductImg(source);
        }

        @Override
        public ActivityProductImg[] newArray(int size) {
            return new ActivityProductImg[size];
        }
    };
}
