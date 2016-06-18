package com.mirroreye.mirror.bean;

/**
 * Created by ${jl} on 16/6/18.
 */
public class LoginBean {

    /**
     * result : 1
     * msg :
     * data : {"token":"ffe6f5d40a8b0c366aa5214caba2c4b1","uid":"104"}
     */

    private String result;
    private String msg;
    /**
     * token : ffe6f5d40a8b0c366aa5214caba2c4b1
     * uid : 104
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String token;
        private String uid;

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
