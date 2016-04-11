package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-4-8.
 */
public class BannerActivity {


    /**
     * ActivityId : 6
     * ActivityName : 夏不为利
     * BannerWebImg : /Resources/BannerActivity/image/20150910/5.jpg
     * BannerAppImg : /Resources/BannerActivity/image/20150910/5_1.jpg
     * ActivityState : 1
     * Url :
     * Introduce :
     * ActivityRank : 1
     * TempBeginTimeStr : 2016-04-01
     * TempEndTimeStr : 2017-01-01
     * WeekTimeStr :
     * ActivityTime : {"ActivityType":1,"ActivityTimeStr":"2016-04-01 00:00:00;2017-01-01 00:00:00","SystemTime":"2016-04-08 15:58:10"}
     * ActivityTag : 4
     */

    private int ActivityId;
    private String ActivityName;
    private String BannerWebImg;
    private String BannerAppImg;
    private int ActivityState;
    private String Url;
    private String Introduce;
    private int ActivityRank;
    private String TempBeginTimeStr;
    private String TempEndTimeStr;
    private String WeekTimeStr;
    /**
     * ActivityType : 1
     * ActivityTimeStr : 2016-04-01 00:00:00;2017-01-01 00:00:00
     * SystemTime : 2016-04-08 15:58:10
     */

    private ActivityTimeEntity ActivityTime;
    private int ActivityTag;

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int ActivityId) {
        this.ActivityId = ActivityId;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String ActivityName) {
        this.ActivityName = ActivityName;
    }

    public String getBannerWebImg() {
        return BannerWebImg;
    }

    public void setBannerWebImg(String BannerWebImg) {
        this.BannerWebImg = BannerWebImg;
    }

    public String getBannerAppImg() {
        return BannerAppImg;
    }

    public void setBannerAppImg(String BannerAppImg) {
        this.BannerAppImg = BannerAppImg;
    }

    public int getActivityState() {
        return ActivityState;
    }

    public void setActivityState(int ActivityState) {
        this.ActivityState = ActivityState;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String Introduce) {
        this.Introduce = Introduce;
    }

    public int getActivityRank() {
        return ActivityRank;
    }

    public void setActivityRank(int ActivityRank) {
        this.ActivityRank = ActivityRank;
    }

    public String getTempBeginTimeStr() {
        return TempBeginTimeStr;
    }

    public void setTempBeginTimeStr(String TempBeginTimeStr) {
        this.TempBeginTimeStr = TempBeginTimeStr;
    }

    public String getTempEndTimeStr() {
        return TempEndTimeStr;
    }

    public void setTempEndTimeStr(String TempEndTimeStr) {
        this.TempEndTimeStr = TempEndTimeStr;
    }

    public String getWeekTimeStr() {
        return WeekTimeStr;
    }

    public void setWeekTimeStr(String WeekTimeStr) {
        this.WeekTimeStr = WeekTimeStr;
    }

    public ActivityTimeEntity getActivityTime() {
        return ActivityTime;
    }

    public void setActivityTime(ActivityTimeEntity ActivityTime) {
        this.ActivityTime = ActivityTime;
    }

    public int getActivityTag() {
        return ActivityTag;
    }

    public void setActivityTag(int ActivityTag) {
        this.ActivityTag = ActivityTag;
    }

    public static class ActivityTimeEntity implements Parcelable {
        private int ActivityType;
        private String ActivityTimeStr;
        private String SystemTime;

        public int getActivityType() {
            return ActivityType;
        }

        public void setActivityType(int ActivityType) {
            this.ActivityType = ActivityType;
        }

        public String getActivityTimeStr() {
            return ActivityTimeStr;
        }

        public void setActivityTimeStr(String ActivityTimeStr) {
            this.ActivityTimeStr = ActivityTimeStr;
        }

        public String getSystemTime() {
            return SystemTime;
        }

        public void setSystemTime(String SystemTime) {
            this.SystemTime = SystemTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.ActivityType);
            dest.writeString(this.ActivityTimeStr);
            dest.writeString(this.SystemTime);
        }

        public ActivityTimeEntity() {
        }

        protected ActivityTimeEntity(Parcel in) {
            this.ActivityType = in.readInt();
            this.ActivityTimeStr = in.readString();
            this.SystemTime = in.readString();
        }

        public static final Parcelable.Creator<ActivityTimeEntity> CREATOR = new Parcelable.Creator<ActivityTimeEntity>() {
            @Override
            public ActivityTimeEntity createFromParcel(Parcel source) {
                return new ActivityTimeEntity(source);
            }

            @Override
            public ActivityTimeEntity[] newArray(int size) {
                return new ActivityTimeEntity[size];
            }
        };
    }
}
