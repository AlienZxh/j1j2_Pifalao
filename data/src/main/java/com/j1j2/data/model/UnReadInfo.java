package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-28.
 */
public class UnReadInfo {

    /**
     * UnReadOrderCount : 0
     * UnReadPushMessageCount : 0
     * UserFavoritesCount : 1
     */

    private int UnReadOrderCount;
    private int UnReadPushMessageCount;
    private int UserFavoritesCount;
    private int FoldRedPacketCount;

    public int getUnReadOrderCount() {
        return UnReadOrderCount;
    }

    public void setUnReadOrderCount(int UnReadOrderCount) {
        this.UnReadOrderCount = UnReadOrderCount;
    }

    public int getUnReadPushMessageCount() {
        return UnReadPushMessageCount;
    }

    public void setUnReadPushMessageCount(int UnReadPushMessageCount) {
        this.UnReadPushMessageCount = UnReadPushMessageCount;
    }

    public int getUserFavoritesCount() {
        return UserFavoritesCount;
    }

    public void setUserFavoritesCount(int UserFavoritesCount) {
        this.UserFavoritesCount = UserFavoritesCount;
    }

    public int getFoldRedPacketCount() {
        return FoldRedPacketCount;
    }

    public void setFoldRedPacketCount(int foldRedPacketCount) {
        FoldRedPacketCount = foldRedPacketCount;
    }

    @Override
    public String toString() {
        return "UnReadInfo{" +
                "UnReadOrderCount=" + UnReadOrderCount +
                ", UnReadPushMessageCount=" + UnReadPushMessageCount +
                ", UserFavoritesCount=" + UserFavoritesCount +
                ", FoldRedPacketCount=" + FoldRedPacketCount +
                '}';
    }
}
