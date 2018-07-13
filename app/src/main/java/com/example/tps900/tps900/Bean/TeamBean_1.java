package com.example.tps900.tps900.Bean;

import java.io.Serializable;
import java.util.List;


/**
 * 项目名称：Galasys_PM
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/6/1 15:55
 * 修改人：zxh
 * 修改时间：2017/6/1 15:55
 * 修改备注：
 */

public class TeamBean_1 implements Serializable {

    /**
     * Data : {"TeamOrders":[{"ITEMSID":"00000735","ORDERID":"00000518","PARTNERNAME":"存存旅行社","ORDERNO":"T00000518","PRODUCTID":"00001197","PRODUCTNAME":"街心公园","PRODUCTPRICE":0.01,"PRODUCTNUM":3,"ECODE":"195372800240","ORDERAMT":0.03,"PLAYDATE":"2017-06-02 00:00:00","PAYTYPE":"0","PARKID":"00000205","REMARK":null,"ISPAY":"0","ISPRINT":"0","OFFCODE":null}],"TeamGuides":[{"GuideName":"11","GuidePhone":"22","CardNo":null}]}
     * Success : true
     * Message : null
     */

    public DataBean Data;
    public boolean Success;
    public Object Message;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean data) {
        Data = data;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object message) {
        Message = message;
    }

    public static class DataBean implements Serializable {
        /**
         * TeamOrders : [{"ITEMSID":"00000735","ORDERID":"00000518","PARTNERNAME":"存存旅行社","ORDERNO":"T00000518","PRODUCTID":"00001197","PRODUCTNAME":"街心公园","PRODUCTPRICE":0.01,"PRODUCTNUM":3,"ECODE":"195372800240","ORDERAMT":0.03,"PLAYDATE":"2017-06-02 00:00:00","PAYTYPE":"0","PARKID":"00000205","REMARK":null,"ISPAY":"0","ISPRINT":"0","OFFCODE":null}]
         * TeamGuides : [{"GuideName":"11","GuidePhone":"22","CardNo":null}]
         */

        public List<TeamOrdersBean> TeamOrders;
        public List<TeamGuidesBean> TeamGuides;

        public List<TeamOrdersBean> getTeamOrders() {
            return TeamOrders;
        }

        public void setTeamOrders(List<TeamOrdersBean> teamOrders) {
            TeamOrders = teamOrders;
        }

        public List<TeamGuidesBean> getTeamGuides() {
            return TeamGuides;
        }

        public void setTeamGuides(List<TeamGuidesBean> teamGuides) {
            TeamGuides = teamGuides;
        }

        public static class TeamOrdersBean implements Serializable {
            /**
             * ITEMSID : 00000735
             * ORDERID : 00000518
             * PARTNERNAME : 存存旅行社
             * ORDERNO : T00000518
             * PRODUCTID : 00001197
             * PRODUCTNAME : 街心公园
             * PRODUCTPRICE : 0.01
             * PRODUCTNUM : 3
             * ECODE : 195372800240
             * ORDERAMT : 0.03
             * PLAYDATE : 2017-06-02 00:00:00
             * PAYTYPE : 0
             * PARKID : 00000205
             * REMARK : null
             * ISPAY : 0
             * ISPRINT : 0
             * OFFCODE : null
             */

            public String ITEMSID;
            public String ORDERID;
            public String PARTNERNAME;
            public String ORDERNO;
            public String PRODUCTID;
            public String PRODUCTNAME;
            public double PRODUCTPRICE;
            public int PRODUCTNUM;
            public String ECODE;
            public double ORDERAMT;
            public String PLAYDATE;
            public String PAYTYPE;
            public String PARKID;
            public Object REMARK;
            public String ISPAY;
            public String ISPRINT;
            public Object OFFCODE;
            public String ActualPerson;

            public String getActualPerson() {
                return ActualPerson;
            }

            public void setActualPerson(String actualPerson) {
                ActualPerson = actualPerson;
            }

            public String getITEMSID() {
                return ITEMSID;
            }

            public void setITEMSID(String ITEMSID) {
                this.ITEMSID = ITEMSID;
            }

            public String getORDERID() {
                return ORDERID;
            }

            public void setORDERID(String ORDERID) {
                this.ORDERID = ORDERID;
            }

            public String getPARTNERNAME() {
                return PARTNERNAME;
            }

            public void setPARTNERNAME(String PARTNERNAME) {
                this.PARTNERNAME = PARTNERNAME;
            }

            public String getORDERNO() {
                return ORDERNO;
            }

            public void setORDERNO(String ORDERNO) {
                this.ORDERNO = ORDERNO;
            }

            public String getPRODUCTID() {
                return PRODUCTID;
            }

            public void setPRODUCTID(String PRODUCTID) {
                this.PRODUCTID = PRODUCTID;
            }

            public String getPRODUCTNAME() {
                return PRODUCTNAME;
            }

            public void setPRODUCTNAME(String PRODUCTNAME) {
                this.PRODUCTNAME = PRODUCTNAME;
            }

            public double getPRODUCTPRICE() {
                return PRODUCTPRICE;
            }

            public void setPRODUCTPRICE(double PRODUCTPRICE) {
                this.PRODUCTPRICE = PRODUCTPRICE;
            }

            public int getPRODUCTNUM() {
                return PRODUCTNUM;
            }

            public void setPRODUCTNUM(int PRODUCTNUM) {
                this.PRODUCTNUM = PRODUCTNUM;
            }

            public String getECODE() {
                return ECODE;
            }

            public void setECODE(String ECODE) {
                this.ECODE = ECODE;
            }

            public double getORDERAMT() {
                return ORDERAMT;
            }

            public void setORDERAMT(double ORDERAMT) {
                this.ORDERAMT = ORDERAMT;
            }

            public String getPLAYDATE() {
                return PLAYDATE;
            }

            public void setPLAYDATE(String PLAYDATE) {
                this.PLAYDATE = PLAYDATE;
            }

            public String getPAYTYPE() {
                return PAYTYPE;
            }

            public void setPAYTYPE(String PAYTYPE) {
                this.PAYTYPE = PAYTYPE;
            }

            public String getPARKID() {
                return PARKID;
            }

            public void setPARKID(String PARKID) {
                this.PARKID = PARKID;
            }

            public Object getREMARK() {
                return REMARK;
            }

            public void setREMARK(Object REMARK) {
                this.REMARK = REMARK;
            }

            public String getISPAY() {
                return ISPAY;
            }

            public void setISPAY(String ISPAY) {
                this.ISPAY = ISPAY;
            }

            public String getISPRINT() {
                return ISPRINT;
            }

            public void setISPRINT(String ISPRINT) {
                this.ISPRINT = ISPRINT;
            }

            public Object getOFFCODE() {
                return OFFCODE;
            }

            public void setOFFCODE(Object OFFCODE) {
                this.OFFCODE = OFFCODE;
            }
        }

        public static class TeamGuidesBean implements Serializable {
            /**
             * GuideName : 11
             * GuidePhone : 22
             * CardNo : null
             */

            public String GuideName;
            public String GuidePhone;
            public String CardNo;

            public String getGuideName() {
                return GuideName;
            }

            public void setGuideName(String guideName) {
                GuideName = guideName;
            }

            public String getGuidePhone() {
                return GuidePhone;
            }

            public void setGuidePhone(String guidePhone) {
                GuidePhone = guidePhone;
            }

            public String getCardNo() {
                return CardNo;
            }

            public void setCardNo(String cardNo) {
                CardNo = cardNo;
            }
        }
    }
}
