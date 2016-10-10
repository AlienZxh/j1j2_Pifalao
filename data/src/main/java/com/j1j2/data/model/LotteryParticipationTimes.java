package com.j1j2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by alienzxh on 16-9-14.
 */
public class LotteryParticipationTimes implements Parcelable {
    /// <summary>
    ///参与人电话
    /// </summary>
    private String UserMobile;

    /// <summary>
    /// 参与人电话 加密字符串
    /// </summary>
    private String UserMobileEncrypt;

    /// <summary>
    /// 抽奖活动参与次数
    /// </summary>
    private int ParticipationTimes;

    /// <summary>
    /// 参与抽奖活动时间
    /// </summary>
    private String ParticipateTimeStr;

    /// <summary>
    /// 参与活动 的奖票号
    /// </summary>
    private List<String> TicketsNum;

    public List<String> getTicketsNum() {
        return TicketsNum;
    }

    public void setTicketsNum(List<String> ticketsNum) {
        TicketsNum = ticketsNum;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public String getUserMobileEncrypt() {
        return UserMobileEncrypt;
    }

    public void setUserMobileEncrypt(String userMobileEncrypt) {
        UserMobileEncrypt = userMobileEncrypt;
    }

    public int getParticipationTimes() {
        return ParticipationTimes;
    }

    public void setParticipationTimes(int participationTimes) {
        ParticipationTimes = participationTimes;
    }

    public String getParticipateTimeStr() {
        return ParticipateTimeStr;
    }

    public void setParticipateTimeStr(String participateTimeStr) {
        ParticipateTimeStr = participateTimeStr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UserMobile);
        dest.writeString(this.UserMobileEncrypt);
        dest.writeInt(this.ParticipationTimes);
        dest.writeString(this.ParticipateTimeStr);
        dest.writeStringList(this.TicketsNum);
    }

    public LotteryParticipationTimes() {
    }

    protected LotteryParticipationTimes(Parcel in) {
        this.UserMobile = in.readString();
        this.UserMobileEncrypt = in.readString();
        this.ParticipationTimes = in.readInt();
        this.ParticipateTimeStr = in.readString();
        this.TicketsNum = in.createStringArrayList();
    }

    public static final Creator<LotteryParticipationTimes> CREATOR = new Creator<LotteryParticipationTimes>() {
        @Override
        public LotteryParticipationTimes createFromParcel(Parcel source) {
            return new LotteryParticipationTimes(source);
        }

        @Override
        public LotteryParticipationTimes[] newArray(int size) {
            return new LotteryParticipationTimes[size];
        }
    };
}
