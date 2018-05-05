package com.pkast.user.model;

public class EncryUserInfo {
    // 用户登录凭证（有效期五分钟）
    private String code;

    //包括敏感数据在内的完整用户信息的加密数据
    private String encryptedData;

    //加密算法的初始向量
    private String iv;

    private String signature;

    private String userRawData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUserRawData() {
        return userRawData;
    }

    public void setUserRawData(String userRawData) {
        this.userRawData = userRawData;
    }
}
