package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-29.
 */
public class HotKey {

    /**
     * Id : 1
     * HotKey : 王老吉
     * Rank : 1
     * ModuleId : 23
     */

    private int Id;
    private String HotKey;
    private int Rank;
    private int ModuleId;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getHotKey() {
        return HotKey;
    }

    public void setHotKey(String HotKey) {
        this.HotKey = HotKey;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int Rank) {
        this.Rank = Rank;
    }

    public int getModuleId() {
        return ModuleId;
    }

    public void setModuleId(int ModuleId) {
        this.ModuleId = ModuleId;
    }

    @Override
    public String toString() {
        return "HotKey{" +
                "Id=" + Id +
                ", HotKey='" + HotKey + '\'' +
                ", Rank=" + Rank +
                ", ModuleId=" + ModuleId +
                '}';
    }
}
