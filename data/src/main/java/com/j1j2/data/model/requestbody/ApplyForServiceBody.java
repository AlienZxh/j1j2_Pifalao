package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-9-25.
 */

public class ApplyForServiceBody {

    private String Mobile;

    /// <summary>
    /// 服务模块ID
    /// </summary>
    private int ServiceModuleId;

    /// <summary>
    /// 服务点
    /// </summary>
    private int ServicePointId;

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getServiceModuleId() {
        return ServiceModuleId;
    }

    public void setServiceModuleId(int serviceModuleId) {
        ServiceModuleId = serviceModuleId;
    }

    public int getServicePointId() {
        return ServicePointId;
    }

    public void setServicePointId(int servicePointId) {
        ServicePointId = servicePointId;
    }
}
