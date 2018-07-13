package com.example.tps900.tps900.Bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by wo on 2017/4/26.
 */

public class LoginBean {

    /**
     * dt : [{"EMP_ID":1925,"EMP_NAME":"王磊"}]
     * isSuccess : true
     * error : null
     */

    public boolean isSuccess;
    public String error;
    public List<DtBean> dt;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DtBean> getDt() {
        return dt;
    }

    public void setDt(List<DtBean> dt) {
        this.dt = dt;
    }

    public static class DtBean {
        /**
         * EMP_ID : 1925
         * EMP_NAME : 王磊
         */
        @JSONField(name = "EMP_ID")
        public int EMP_IDX;
        @JSONField(name = "EMP_NAME")
        public String EMP_NAMEX;

        public int getEMP_IDX() {
            return EMP_IDX;
        }

        public void setEMP_IDX(int EMP_IDX) {
            this.EMP_IDX = EMP_IDX;
        }

        public String getEMP_NAMEX() {
            return EMP_NAMEX;
        }

        public void setEMP_NAMEX(String EMP_NAMEX) {
            this.EMP_NAMEX = EMP_NAMEX;
        }
    }
}
