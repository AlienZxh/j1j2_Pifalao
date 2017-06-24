package com.j1j2.pifalao.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-12-20.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pifalao.db";
    private static final int DATABASE_VERSION = 1;

    private UserRelativePreference userRelativePreference;

    public DBHelper(Context context, UserRelativePreference userRelativePreference) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.userRelativePreference = userRelativePreference;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logger.d("DBHelper:onCreate");

        userRelativePreference.removeSelectedCity();
        userRelativePreference.removeSelectedServicePoint();
        userRelativePreference.removeSelectedModule();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.d("DBHelper:onUpgrade  oldVersion:" + oldVersion + " newVersion:" + newVersion);
    }
}
