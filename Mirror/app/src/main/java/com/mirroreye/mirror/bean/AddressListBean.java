package com.mirroreye.mirror.bean;

import java.util.List;

/**
 * Created by ${jl} on 16/6/21.
 */
public class AddressListBean {

    /**
     * result : 1
     * msg :
     * data : {"has_more":"2","list":[{"addr_id":"1257","username":"11","cellphone":"13298895308","addr_info":"cc","if_moren":"2","id_card":""},{"addr_id":"1256","username":"\"降生要\"","cellphone":"","addr_info":"\"这是一个美丽的地方亚麻亚厚黑\"","if_moren":"1","id_card":""},{"addr_id":"1255","username":"11","cellphone":"13298895308","addr_info":"cc","if_moren":"2","id_card":""}]}
     */

    private String result;
    private String msg;
    /**
     * has_more : 2
     * list : [{"addr_id":"1257","username":"11","cellphone":"13298895308","addr_info":"cc","if_moren":"2","id_card":""},{"addr_id":"1256","username":"\"降生要\"","cellphone":"","addr_info":"\"这是一个美丽的地方亚麻亚厚黑\"","if_moren":"1","id_card":""},{"addr_id":"1255","username":"11","cellphone":"13298895308","addr_info":"cc","if_moren":"2","id_card":""}]
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
        private String has_more;
        /**
         * addr_id : 1257
         * username : 11
         * cellphone : 13298895308
         * addr_info : cc
         * if_moren : 2
         * id_card :
         */

        private List<ListBean> list;

        public String getHas_more() {
            return has_more;
        }

        public void setHas_more(String has_more) {
            this.has_more = has_more;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String addr_id;
            private String username;
            private String cellphone;
            private String addr_info;
            private String if_moren;
            private String id_card;

            public String getAddr_id() {
                return addr_id;
            }

            public void setAddr_id(String addr_id) {
                this.addr_id = addr_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCellphone() {
                return cellphone;
            }

            public void setCellphone(String cellphone) {
                this.cellphone = cellphone;
            }

            public String getAddr_info() {
                return addr_info;
            }

            public void setAddr_info(String addr_info) {
                this.addr_info = addr_info;
            }

            public String getIf_moren() {
                return if_moren;
            }

            public void setIf_moren(String if_moren) {
                this.if_moren = if_moren;
            }

            public String getId_card() {
                return id_card;
            }

            public void setId_card(String id_card) {
                this.id_card = id_card;
            }
        }
    }
}
