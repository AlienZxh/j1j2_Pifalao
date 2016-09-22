package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-3-11.
 */
public class WebReturn<T> {
    private boolean Value;

    private T Detail;

    private String ErrorMessage;

    private String OtherMessage;

    public boolean isValue() {
        return Value;
    }

    public void setValue(boolean value) {
        Value = value;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public T getDetail() {
        return Detail;
    }

    public void setDetail(T detail) {
        Detail = detail;
    }

    public String getOtherMessage() {
        return OtherMessage;
    }

    public void setOtherMessage(String otherMessage) {
        OtherMessage = otherMessage;
    }

    @Override
    public String toString() {
        return "WebReturn{" +
                "Value=" + Value +
                ", Detail=" + Detail +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                ", OtherMessage='" + OtherMessage + '\'' +
                '}';
    }
}
