package com.mirroreye.mirror.ui.login;

import java.util.List;

/**
 * Created by 秦谦谦 on 16/6/24 19:49.
 */
public class RegisteredBean {
    private String result;
    private String msg;
    private List<data> dataList;

    public RegisteredBean(String result, String msg, List<data> dataList) {
        this.result = result;
        this.msg = msg;
        this.dataList = dataList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<data> getDataList() {
        return dataList;
    }

    public void setDataList(List<data> dataList) {
        this.dataList = dataList;
    }

    public static class data {
        private String token;
        private String uid;


        public data(String token, String uid) {
            this.token = token;
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}