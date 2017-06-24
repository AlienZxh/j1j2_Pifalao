package com.j1j2.pifalao.app.sharedpreferences;

import com.j1j2.data.model.User;
import com.j1j2.pifalao.app.sharedpreferences.TypeAdapter.UserInfoAdapter;

import net.orange_box.storebox.annotations.method.KeyByString;
import net.orange_box.storebox.annotations.method.RemoveMethod;
import net.orange_box.storebox.annotations.method.TypeAdapter;
import net.orange_box.storebox.annotations.option.SaveOption;
import net.orange_box.storebox.enums.SaveMode;

/**
 * Created by alienzxh on 16-3-18.
 */
public interface UserLoginPreference {
    String KEY_AUTOLOGIN = "key_autologin";
    String KEY_USERNAME = "key_username";
    String KEY_PASSWORD = "key_password";
    String KEY_LOGINCOOKIE = "key_logincookie";
    String KEY_USERINFO = "key_userinfo";

    @KeyByString(KEY_AUTOLOGIN)
    boolean getIsAutoLogin(boolean mDefault);

    @KeyByString(KEY_AUTOLOGIN)
    @SaveOption(SaveMode.COMMIT)
    UserLoginPreference setIsAutoLogin(boolean isAutoLogin);

    @KeyByString(KEY_AUTOLOGIN)
    @RemoveMethod
    @SaveOption(SaveMode.COMMIT)
    UserLoginPreference removeIsAutoLogin();


    @KeyByString(KEY_LOGINCOOKIE)
    String getLoginCookie(String mDefault);

    @KeyByString(KEY_LOGINCOOKIE)
    @SaveOption(SaveMode.COMMIT)
    UserLoginPreference setLoginCookie(String loginCookie);

    @KeyByString(KEY_LOGINCOOKIE)
    @RemoveMethod
    @SaveOption(SaveMode.COMMIT)
    UserLoginPreference removeLoginCookie();


    @KeyByString(KEY_USERNAME)
    String getUsername(String mDefault);

    @KeyByString(KEY_USERNAME)
    UserLoginPreference setUserName(String userName);

    @KeyByString(KEY_USERNAME)
    @RemoveMethod
    UserLoginPreference removeUserName();

    @KeyByString(KEY_PASSWORD)
    String getPassWord(String mDefault);

    @KeyByString(KEY_PASSWORD)
    UserLoginPreference setPassWord(String passWord);

    @KeyByString(KEY_PASSWORD)
    @RemoveMethod
    UserLoginPreference removePassWord();


    @KeyByString(KEY_USERINFO)
    @TypeAdapter(UserInfoAdapter.class)
    User getUserInfo(User user);

    @KeyByString(KEY_USERINFO)
    @TypeAdapter(UserInfoAdapter.class)
    UserLoginPreference setUserInfo(User userInfo);

    @KeyByString(KEY_USERINFO)
    @RemoveMethod
    UserLoginPreference removeUserInfo();
}
