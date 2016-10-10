package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-9-26.
 */

public class ApplyForServiceCount {

    private int Count;
    private boolean CurrentUserApplyed;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public boolean isCurrentUserApplyed() {
        return CurrentUserApplyed;
    }

    public void setCurrentUserApplyed(boolean currentUserApplyed) {
        CurrentUserApplyed = currentUserApplyed;
    }

    @Override
    public String toString() {
        return "ApplyForServiceCount{" +
                "Count=" + Count +
                ", CurrentUserApplyed=" + CurrentUserApplyed +
                '}';
    }
}
