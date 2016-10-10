package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-9-24.
 */

public class ImgUrl implements Parcelable {

    private String url;

    public ImgUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    public ImgUrl() {
    }

    protected ImgUrl(Parcel in) {
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ImgUrl> CREATOR = new Parcelable.Creator<ImgUrl>() {
        @Override
        public ImgUrl createFromParcel(Parcel source) {
            return new ImgUrl(source);
        }

        @Override
        public ImgUrl[] newArray(int size) {
            return new ImgUrl[size];
        }
    };
}
