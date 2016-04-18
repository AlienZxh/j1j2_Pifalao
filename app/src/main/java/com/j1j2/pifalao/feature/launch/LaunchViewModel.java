package com.j1j2.pifalao.feature.launch;

import android.os.Environment;

import com.j1j2.data.http.api.UserLoginApi;
import com.j1j2.data.model.UpdateInfo;
import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.request.RequestCall;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-19.
 */
public class LaunchViewModel {

    private UserLoginApi userLoginApi;
    private LaunchActivity launchActivity;
    private UserLoginPreference userLoginPreference;
    /* 下载包安装路径 */
    private static final String savePath = Environment
            .getExternalStorageDirectory() + "/Download/";
    private static final String saveFileName = "pifalao.apk";
    private RequestCall downloadAPKCall = OkHttpUtils//
            .get()//
            .url(BuildConfig.APK_URL)//
            .tag("downloadAPK")//
            .build();

    public LaunchViewModel(UserLoginApi userLoginApi, LaunchActivity launchActivity, UserLoginPreference userLoginPreference) {
        this.userLoginApi = userLoginApi;
        this.launchActivity = launchActivity;
        this.userLoginPreference = userLoginPreference;
    }

    public void downloadAPK() {
        launchActivity.showDownloadDialog();
        downloadAPKCall.execute(new FileCallBack(savePath, saveFileName)//
        {
            int lastProgress = 0;
            int currentPregress = 0;

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);

            }

            @Override
            public void inProgress(float progress, long total) {
                currentPregress = (int) (progress * 100);
                if (lastProgress < currentPregress) {
                    lastProgress = currentPregress;
                    launchActivity.setDownloadProgress(currentPregress, total);
                }

            }

            @Override
            public void onError(Call call, Exception e) {
                launchActivity.hideDownloadDialog();
            }

            @Override
            public void onResponse(File response) {
                launchActivity.hideDownloadDialog();
                launchActivity.installAPK(response);
                launchActivity.finish();
            }
        });
    }

    public void cancelDownloadAPK() {
        if (null != downloadAPKCall)
            downloadAPKCall.cancel();
    }

    public void getUpdateInfo() {
        OkHttpUtils
                .get()
                .url(BuildConfig.UPDATE_URL)
                .build()
                .execute(new Callback<UpdateInfo>() {
                    @Override
                    public UpdateInfo parseNetworkResponse(Response response) throws Exception {
                        return parseXml(response.body().byteStream());
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        initLoginState();
                    }

                    @Override
                    public void onResponse(UpdateInfo updateInfo) {
                        if (isUpdate(updateInfo, launchActivity.getVersionCode())) {
                            if (updateInfo.isCompulsory()) {
                                launchActivity.showCompulsoryUpdateDialog();
                            } else {
                                launchActivity.showUpdateDialog();
                            }
                        } else {
                            initLoginState();
                        }
                    }
                });
    }

    public void initLoginState() {
        LoginBody loginBody = new LoginBody();
        loginBody.setLoginAccount(userLoginPreference.getUsername(""));
        loginBody.setPassWord(userLoginPreference.getPassWord(""));
        loginBody.setUserType(5);
        loginBody.setTerminalType(1);
        userLoginApi.login(loginBody)
                .delay(1, TimeUnit.SECONDS)
                .compose(launchActivity.<WebReturn<User>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new WebReturnSubscriber<User>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        MainAplication.get(launchActivity).loginOut();
                        EventBus.getDefault().postSticky(new LogStateEvent(false));
                        launchActivity.navigateTo();
                    }

                    @Override
                    public void onWebReturnSucess(User user) {
                        if (userLoginPreference.getIsAutoLogin(false)) {
                            MainAplication.get(launchActivity).login(user);
                            EventBus.getDefault().postSticky(new LogStateEvent(true));
                        } else {
                            MainAplication.get(launchActivity).loginOut();
                            EventBus.getDefault().postSticky(new LogStateEvent(false));
                        }
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        MainAplication.get(launchActivity).loginOut();
                        EventBus.getDefault().postSticky(new LogStateEvent(false));
                    }

                    @Override
                    public void onWebReturnCompleted() {
                        launchActivity.navigateTo();
                    }
                });
    }

    public UpdateInfo parseXml(InputStream inStream) {
        UpdateInfo updateInfo = new UpdateInfo();
        // 实例化一个文档构建器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        Document document = null;
        try {
            // 通过文档构建器工厂获取一个文档构建器
            builder = factory.newDocumentBuilder();
            // 通过文档通过文档构建器构建一个文档实例
            document = builder.parse(inStream);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (builder == null || document == null)
                return null;
        }
        // 获取XML文件根节点
        Element root = document.getDocumentElement();
        // 获得所有子节点
        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            // 遍历子节点
            Node childNode = (Node) childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                // 版本号VersionCode
                if ("EdtionNum".equals(childElement.getNodeName())) {
                    updateInfo.setVersionCode(Integer.parseInt(childElement.getFirstChild()
                            .getNodeValue()));
                }
                // 版本名称
                if ("VersionName".equals(childElement.getNodeName())) {
                    updateInfo.setVersionName(childElement.getFirstChild()
                            .getNodeValue());
                }
                // 软件名称
                else if (("SoftName".equals(childElement.getNodeName()))) {
                    updateInfo.setSoftName(childElement.getFirstChild()
                            .getNodeValue());
                }
                // 下载地址
                else if (("SoftURL".equals(childElement.getNodeName()))) {
                    updateInfo.setSoftUrl(childElement.getFirstChild()
                            .getNodeValue());
                }
                // 是否强制升级
                else if (("IsCompulsory".equals(childElement.getNodeName()))) {
                    updateInfo.setCompulsory(Boolean.parseBoolean(childElement.getFirstChild()
                            .getNodeValue()));
                }
            }
        }
        return updateInfo;
    }

    public boolean isUpdate(UpdateInfo updateInfo, int versingCode) {
        if (updateInfo != null) {
            if (versingCode > 0 && versingCode < updateInfo.getVersionCode()) {
                return true;
            }
        }
        return false;
    }


}
