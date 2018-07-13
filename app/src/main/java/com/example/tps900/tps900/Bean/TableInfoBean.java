package com.example.tps900.tps900.Bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-03-09 10:17
 * 修改人：zxh
 * 修改时间：2018-03-09 10:17
 * 修改备注：
 */
public class TableInfoBean {
    /**
     * dt : [{"NID":40,"DDESKNUMBER":"A001","DESKNAME":"1号桌","DPRICE":100,"DNUMBER":4,"DREMARKS":"","DNGZONEID":2556,"DCREATTIME":"2016-10-28T10:36:47","UPDATETIME":"2016-11-16T17:31:36","ISENABLE":0,"DTABLETYPE":2},{"NID":41,"DDESKNUMBER":"A002","DESKNAME":"2号台","DPRICE":100,"DNUMBER":4,"DREMARKS":"A002","DNGZONEID":2556,"DCREATTIME":"2016-10-28T10:50:17","UPDATETIME":"2016-11-01T21:19:12","ISENABLE":0,"DTABLETYPE":2}]
     * isSuccess : false
     * error : null
     */

    public boolean isSuccess;
    public String  error;
    public ArrayList<DtBean> dt;

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

    public ArrayList<DtBean> getDt() {
        return dt;
    }

    public void setDt(ArrayList<DtBean> dt) {
        this.dt = dt;
    }

    public static class DtBean implements Serializable {
        /**
         * NID : 40.0
         * DDESKNUMBER : A001
         * DESKNAME : 1号桌
         * DPRICE : 100.0
         * DNUMBER : 4
         * DREMARKS :
         * DNGZONEID : 2556.0
         * DCREATTIME : 2016-10-28T10:36:47
         * UPDATETIME : 2016-11-16T17:31:36
         * ISENABLE : 0
         * DTABLETYPE : 2
         */
        public int Ticketnumber;

        //主键ID
        public int NID;
        //桌号
        public String DDESKNUMBER;
        //桌名
        public String DESKNAME;
        //价格
        public double DPRICE;
        //该桌标准人数
        public int DNUMBER;
        //备注
        public String DREMARKS;
        //营业点ID
        public String DNGZONEID;
        //创建时间
        public String DCREATTIME;
        //修改时间
        public String UPDATETIME;
        //是否可用
        public int ISEnable;
        //桌子类型
        public int DTABLETYPE;

        public int getNID() {
            return NID;
        }

        public void setNID(int NID) {
            this.NID = NID;
        }

        public String getDDESKNUMBER() {
            return DDESKNUMBER;
        }

        public void setDDESKNUMBER(String DDESKNUMBER) {
            this.DDESKNUMBER = DDESKNUMBER;
        }

        public String getDESKNAME() {
            return DESKNAME;
        }

        public void setDESKNAME(String DESKNAME) {
            this.DESKNAME = DESKNAME;
        }

        public double getDPRICE() {
            return DPRICE;
        }

        public void setDPRICE(double DPRICE) {
            this.DPRICE = DPRICE;
        }

        public int getDNUMBER() {
            return DNUMBER;
        }

        public void setDNUMBER(int DNUMBER) {
            this.DNUMBER = DNUMBER;
        }

        public String getDREMARKS() {
            return DREMARKS;
        }

        public void setDREMARKS(String DREMARKS) {
            this.DREMARKS = DREMARKS;
        }

        public String getDNGZONEID() {
            return DNGZONEID;
        }

        public void setDNGZONEID(String DNGZONEID) {
            this.DNGZONEID = DNGZONEID;
        }

        public String getDCREATTIME() {
            return DCREATTIME;
        }

        public void setDCREATTIME(String DCREATTIME) {
            this.DCREATTIME = DCREATTIME;
        }

        public String getUPDATETIME() {
            return UPDATETIME;
        }

        public void setUPDATETIME(String UPDATETIME) {
            this.UPDATETIME = UPDATETIME;
        }

        public int getISEnable() {
            return ISEnable;
        }

        public void setISEnable(int ISEnable) {
            this.ISEnable = ISEnable;
        }

        public int getDTABLETYPE() {
            return DTABLETYPE;
        }

        public void setDTABLETYPE(int DTABLETYPE) {
            this.DTABLETYPE = DTABLETYPE;
        }
        public int getTicketnumber() {
            return Ticketnumber;
        }

        public void setTicketnumber(int ticketnumber) {
            Ticketnumber = ticketnumber;
        }
    }



}
