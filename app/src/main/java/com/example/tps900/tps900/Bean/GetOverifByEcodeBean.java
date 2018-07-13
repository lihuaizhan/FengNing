package com.example.tps900.tps900.Bean;

/**
 * 项目名称：T_TPS900_AFK
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/5/24 20:06
 * 修改人：zxh
 * 修改时间：2017/5/24 20:06
 * 修改备注：
 */

public class GetOverifByEcodeBean {

    /**
     * Data : {"OrderNo":"F00001409","OrderId":null,"ProductId":"00000895","ProductName":"熊猫基地儿童票","ProductCount":"2","ProductPrice":0,"OrderAmt":0,"PlayStartDate":"2017-5-24 0:00:00","PlayEndDate":"2017-8-21 23:59:59","Ecode":"178728843414","CustName":"熊猫基地儿童票","CustPhone":"15287392743","PrintPrice":"0.02","PrintName":"熊猫基地儿童票","CardNo":"463721864372164328","Stime":null,"Etime":null,"ReturnNum":0,"AvailableNum":0,"Incount":0,"IsPrint":null,"OffCode":null}
     * Success : true
     * Message : null
     */

    public DataBean Data;
    public boolean Success;
    public String Message;

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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public static class DataBean {
        /**
         * OrderNo : F00001409
         * OrderId : null
         * ProductId : 00000895
         * ProductName : 熊猫基地儿童票
         * ProductCount : 2
         * ProductPrice : 0
         * OrderAmt : 0
         * PlayStartDate : 2017-5-24 0:00:00
         * PlayEndDate : 2017-8-21 23:59:59
         * Ecode : 178728843414
         * CustName : 熊猫基地儿童票
         * CustPhone : 15287392743
         * PrintPrice : 0.02
         * PrintName : 熊猫基地儿童票
         * CardNo : 463721864372164328
         * Stime : null
         * Etime : null
         * ReturnNum : 0
         * AvailableNum : 0
         * Incount : 0
         * IsPrint : null
         * OffCode : null
         */
        public String PrintTime;
        public String OrderNo;
        public String OrderId;
        public String ProductId;
        public String ProductName;
        public String ProductCount;
        public int ProductPrice;
        public int OrderAmt;
        public String PlayStartDate;
        public String PlayEndDate;
        public String Ecode;
        public String CustName;
        public String CustPhone;
        public String PrintPrice;
        public String PrintName;
        public String CardNo;
        public Object Stime;
        public Object Etime;
        public int ReturnNum;
        public int AvailableNum;
        public int Incount;
        public String IsPrint;
        public String OffCode;

        public String getPrintTime() {
            return PrintTime;
        }

        public void setPrintTime(String printTime) {
            PrintTime = printTime;
        }

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String orderNo) {
            OrderNo = orderNo;
        }

        public String getOrderId() {
            return OrderId;
        }

        public void setOrderId(String orderId) {
            OrderId = orderId;
        }

        public String getProductId() {
            return ProductId;
        }

        public void setProductId(String productId) {
            ProductId = productId;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public String getProductCount() {
            return ProductCount;
        }

        public void setProductCount(String productCount) {
            ProductCount = productCount;
        }

        public int getProductPrice() {
            return ProductPrice;
        }

        public void setProductPrice(int productPrice) {
            ProductPrice = productPrice;
        }

        public int getOrderAmt() {
            return OrderAmt;
        }

        public void setOrderAmt(int orderAmt) {
            OrderAmt = orderAmt;
        }

        public String getPlayStartDate() {
            return PlayStartDate;
        }

        public void setPlayStartDate(String playStartDate) {
            PlayStartDate = playStartDate;
        }

        public String getPlayEndDate() {
            return PlayEndDate;
        }

        public void setPlayEndDate(String playEndDate) {
            PlayEndDate = playEndDate;
        }

        public String getEcode() {
            return Ecode;
        }

        public void setEcode(String ecode) {
            Ecode = ecode;
        }

        public String getCustName() {
            return CustName;
        }

        public void setCustName(String custName) {
            CustName = custName;
        }

        public String getCustPhone() {
            return CustPhone;
        }

        public void setCustPhone(String custPhone) {
            CustPhone = custPhone;
        }

        public String getPrintPrice() {
            return PrintPrice;
        }

        public void setPrintPrice(String printPrice) {
            PrintPrice = printPrice;
        }

        public String getPrintName() {
            return PrintName;
        }

        public void setPrintName(String printName) {
            PrintName = printName;
        }

        public String getCardNo() {
            return CardNo;
        }

        public void setCardNo(String cardNo) {
            CardNo = cardNo;
        }

        public Object getStime() {
            return Stime;
        }

        public void setStime(Object stime) {
            Stime = stime;
        }

        public Object getEtime() {
            return Etime;
        }

        public void setEtime(Object etime) {
            Etime = etime;
        }

        public int getReturnNum() {
            return ReturnNum;
        }

        public void setReturnNum(int returnNum) {
            ReturnNum = returnNum;
        }

        public int getAvailableNum() {
            return AvailableNum;
        }

        public void setAvailableNum(int availableNum) {
            AvailableNum = availableNum;
        }

        public int getIncount() {
            return Incount;
        }

        public void setIncount(int incount) {
            Incount = incount;
        }

        public String getIsPrint() {
            return IsPrint;
        }

        public void setIsPrint(String isPrint) {
            IsPrint = isPrint;
        }

        public String getOffCode() {
            return OffCode;
        }

        public void setOffCode(String offCode) {
            OffCode = offCode;
        }
    }
}
