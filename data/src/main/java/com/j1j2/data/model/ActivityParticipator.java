package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-9-14.
 */
public class ActivityParticipator implements Parcelable {
    /// <summary>
    /// 中奖者ID
    /// </summary>
    private int UserId;

    /// <summary>
    /// 获奖者
    /// </summary>
    private String WinnerMobile;

    /// <summary>
    /// 获奖者 加密字符串
    /// </summary>
    private String WinnerEncrypt;


    /// <summary>
    /// 奖票号码（幸运号码）
    /// </summary>
    private String WinTicketNum;


    /// <summary>
    /// 中奖者该期活动参与人次数
    /// </summary>
    private int WinnerParticipationTimes;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getWinnerMobile() {
        return WinnerMobile;
    }

    public void setWinnerMobile(String winnerMobile) {
        WinnerMobile = winnerMobile;
    }

    public String getWinnerEncrypt() {
        return WinnerEncrypt;
    }

    public void setWinnerEncrypt(String winnerEncrypt) {
        WinnerEncrypt = winnerEncrypt;
    }

    public String getWinTicketNum() {
        return WinTicketNum;
    }

    public void setWinTicketNum(String winTicketNum) {
        WinTicketNum = winTicketNum;
    }

    public int getWinnerParticipationTimes() {
        return WinnerParticipationTimes;
    }

    public void setWinnerParticipationTimes(int winnerParticipationTimes) {
        WinnerParticipationTimes = winnerParticipationTimes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.UserId);
        dest.writeString(this.WinnerMobile);
        dest.writeString(this.WinnerEncrypt);
        dest.writeString(this.WinTicketNum);
        dest.writeInt(this.WinnerParticipationTimes);
    }

    public ActivityParticipator() {
    }

    protected ActivityParticipator(Parcel in) {
        this.UserId = in.readInt();
        this.WinnerMobile = in.readString();
        this.WinnerEncrypt = in.readString();
        this.WinTicketNum = in.readString();
        this.WinnerParticipationTimes = in.readInt();
    }

    public static final Parcelable.Creator<ActivityParticipator> CREATOR = new Parcelable.Creator<ActivityParticipator>() {
        @Override
        public ActivityParticipator createFromParcel(Parcel source) {
            return new ActivityParticipator(source);
        }

        @Override
        public ActivityParticipator[] newArray(int size) {
            return new ActivityParticipator[size];
        }
    };
}
