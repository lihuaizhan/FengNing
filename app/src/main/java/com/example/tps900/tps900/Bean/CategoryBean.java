package com.example.tps900.tps900.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wo on 2017/4/27.
 */

public class CategoryBean implements Serializable {


    /**
     * dt : [{"NCLASSID":42,"CODE":"01","SCLASSNAME":"茶"},{"NCLASSID":44,"CODE":"0101","SCLASSNAME":"凉茶"},{"NCLASSID":58,"CODE":"02010101","SCLASSNAME":"魅蓝系列"},{"NCLASSID":97,"CODE":"9521","SCLASSNAME":"饮料类"},{"NCLASSID":98,"CODE":"9512","SCLASSNAME":"食品类"},{"NCLASSID":123,"CODE":"002323","SCLASSNAME":"面包"},{"NCLASSID":216,"CODE":"9715","SCLASSNAME":"玩具公仔"}]
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
         * NCLASSID : 42
         * CODE : 01
         * SCLASSNAME : 茶
         */

        public int NCLASSID;
        public String CODE;
        public String SCLASSNAME;

        public int getNCLASSID() {
            return NCLASSID;
        }

        public void setNCLASSID(int NCLASSID) {
            this.NCLASSID = NCLASSID;
        }

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public String getSCLASSNAME() {
            return SCLASSNAME;
        }

        public void setSCLASSNAME(String SCLASSNAME) {
            this.SCLASSNAME = SCLASSNAME;
        }
    }
}
