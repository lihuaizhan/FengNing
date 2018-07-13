package com.example.tps900.tps900.Bean;

import java.util.List;

/**
 * 项目名称：PDA_XiLing
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/11/22 15:00
 * 修改人：zxh
 * 修改时间：2017/11/22 15:00
 * 修改备注：
 */

public class ZoneInfoBean {


    /**
     * dt : [{"NGZONEID":2556,"SGZONENAME":"1号售票点","NTERMINALID":3352,"STERMINALNAME":"Lucky"}]
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
         * NGZONEID : 2556
         * SGZONENAME : 1号售票点
         * NTERMINALID : 3352
         * STERMINALNAME : Lucky
         */

        public int NGZONEID;
        public String SGZONENAME;
        public int NTERMINALID;
        public String STERMINALNAME;

        public int getNGZONEID() {
            return NGZONEID;
        }

        public void setNGZONEID(int NGZONEID) {
            this.NGZONEID = NGZONEID;
        }

        public String getSGZONENAME() {
            return SGZONENAME;
        }

        public void setSGZONENAME(String SGZONENAME) {
            this.SGZONENAME = SGZONENAME;
        }

        public int getNTERMINALID() {
            return NTERMINALID;
        }

        public void setNTERMINALID(int NTERMINALID) {
            this.NTERMINALID = NTERMINALID;
        }

        public String getSTERMINALNAME() {
            return STERMINALNAME;
        }

        public void setSTERMINALNAME(String STERMINALNAME) {
            this.STERMINALNAME = STERMINALNAME;
        }
    }
}
