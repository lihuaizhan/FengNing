package com.example.tps900.tps900.Bean;


/**
 * 项目名称：T_TPS900_AFK
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/5/24 20:22
 * 修改人：zxh
 * 修改时间：2017/5/24 20:22
 * 修改备注：
 */

public class ConsumeTicketsBean {

    /**
     * Data : {"PARTNERNAME":null,"OrderNo":"PFS00013811","OrderId":null,"ProductId":"00001631","ProductName":"测试票","ProductCount":"1","ProductPrice":0,"OrderAmt":0,"PlayStartDate":"2018/2/27 0:00:00","PlayEndDate":"2019/2/26 23:59:59","Ecode":"632610632322","CustName":"测试票","CustPhone":"152","PrintPrice":"0.01","PrintName":"测试票","CardNo":"12121212121","Stime":null,"Etime":null,"ReturnNum":0,"AvailableNum":0,"Incount":0,"IsPrint":null,"OffCode":null,"CsShow":null,"Seatid":null,"Repid":null,"Areaid":null,"OVERIF_ROWS":null,"OVERIF_COLUMN":null,"Sdate":null,"PrintDate":null,"Paytime":"/Date(-62135596800000)/","Yftime":null,"tyname":null,"companyName":null,"timelist":null,"TureCOUNT":"1","ISBIGTICKETS":null,"deadline":0,"Finalverificationtime":"/Date(-62135596800000)/","Ordernum":null,"signincount":null}
     * Success : true
     * Code : null
     * Message : null
     * dtTime : null
     * Orderno : null
     */

    public DataBean Data;
    public boolean Success;
    public String Code;
    public String Message;
    public String dtTime;
    public String Orderno;

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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDtTime() {
        return dtTime;
    }

    public void setDtTime(String dtTime) {
        this.dtTime = dtTime;
    }

    public String getOrderno() {
        return Orderno;
    }

    public void setOrderno(String orderno) {
        Orderno = orderno;
    }

    public static class DataBean {
        /**
         * PARTNERNAME : null
         * OrderNo : PFS00013811
         * OrderId : null
         * ProductId : 00001631
         * ProductName : 测试票
         * ProductCount : 1
         * ProductPrice : 0
         * OrderAmt : 0
         * PlayStartDate : 2018/2/27 0:00:00
         * PlayEndDate : 2019/2/26 23:59:59
         * Ecode : 632610632322
         * CustName : 测试票
         * CustPhone : 152
         * PrintPrice : 0.01
         * PrintName : 测试票
         * CardNo : 12121212121
         * Stime : null
         * Etime : null
         * ReturnNum : 0
         * AvailableNum : 0
         * Incount : 0
         * IsPrint : null
         * OffCode : null
         * CsShow : null
         * Seatid : null
         * Repid : null
         * Areaid : null
         * OVERIF_ROWS : null
         * OVERIF_COLUMN : null
         * Sdate : null
         * PrintDate : null
         * Paytime : /Date(-62135596800000)/
         * Yftime : null
         * tyname : null
         * companyName : null
         * timelist : null
         * TureCOUNT : 1
         * ISBIGTICKETS : null
         * deadline : 0
         * Finalverificationtime : /Date(-62135596800000)/
         * Ordernum : null
         * signincount : null
         */

        public String PARTNERNAME;
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
        public String Stime;
        public String Etime;
        public int ReturnNum;
        public int AvailableNum;
        public int Incount;
        public String IsPrint;
        public String OffCode;
        public String CsShow;
        public String Seatid;
        public String Repid;
        public String Areaid;
        public String OVERIF_ROWS;
        public String OVERIF_COLUMN;
        public String Sdate;
        public String PrintDate;
        public String Paytime;
        public String Yftime;
        public String tyname;
        public String companyName;
        public String timelist;
        public String TureCOUNT;
        public String ISBIGTICKETS;
        public int deadline;
        public String Finalverificationtime;
        public String Ordernum;
        public String signincount;

        public String getPARTNERNAME() {
            return PARTNERNAME;
        }

        public void setPARTNERNAME(String PARTNERNAME) {
            this.PARTNERNAME = PARTNERNAME;
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

        public String getStime() {
            return Stime;
        }

        public void setStime(String stime) {
            Stime = stime;
        }

        public String getEtime() {
            return Etime;
        }

        public void setEtime(String etime) {
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

        public String getCsShow() {
            return CsShow;
        }

        public void setCsShow(String csShow) {
            CsShow = csShow;
        }

        public String getSeatid() {
            return Seatid;
        }

        public void setSeatid(String seatid) {
            Seatid = seatid;
        }

        public String getRepid() {
            return Repid;
        }

        public void setRepid(String repid) {
            Repid = repid;
        }

        public String getAreaid() {
            return Areaid;
        }

        public void setAreaid(String areaid) {
            Areaid = areaid;
        }

        public String getOVERIF_ROWS() {
            return OVERIF_ROWS;
        }

        public void setOVERIF_ROWS(String OVERIF_ROWS) {
            this.OVERIF_ROWS = OVERIF_ROWS;
        }

        public String getOVERIF_COLUMN() {
            return OVERIF_COLUMN;
        }

        public void setOVERIF_COLUMN(String OVERIF_COLUMN) {
            this.OVERIF_COLUMN = OVERIF_COLUMN;
        }

        public String getSdate() {
            return Sdate;
        }

        public void setSdate(String sdate) {
            Sdate = sdate;
        }

        public String getPrintDate() {
            return PrintDate;
        }

        public void setPrintDate(String printDate) {
            PrintDate = printDate;
        }

        public String getPaytime() {
            return Paytime;
        }

        public void setPaytime(String paytime) {
            Paytime = paytime;
        }

        public String getYftime() {
            return Yftime;
        }

        public void setYftime(String yftime) {
            Yftime = yftime;
        }

        public String getTyname() {
            return tyname;
        }

        public void setTyname(String tyname) {
            this.tyname = tyname;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getTimelist() {
            return timelist;
        }

        public void setTimelist(String timelist) {
            this.timelist = timelist;
        }

        public String getTureCOUNT() {
            return TureCOUNT;
        }

        public void setTureCOUNT(String tureCOUNT) {
            TureCOUNT = tureCOUNT;
        }

        public String getISBIGTICKETS() {
            return ISBIGTICKETS;
        }

        public void setISBIGTICKETS(String ISBIGTICKETS) {
            this.ISBIGTICKETS = ISBIGTICKETS;
        }

        public int getDeadline() {
            return deadline;
        }

        public void setDeadline(int deadline) {
            this.deadline = deadline;
        }

        public String getFinalverificationtime() {
            return Finalverificationtime;
        }

        public void setFinalverificationtime(String finalverificationtime) {
            Finalverificationtime = finalverificationtime;
        }

        public String getOrdernum() {
            return Ordernum;
        }

        public void setOrdernum(String ordernum) {
            Ordernum = ordernum;
        }

        public String getSignincount() {
            return signincount;
        }

        public void setSignincount(String signincount) {
            this.signincount = signincount;
        }
    }
}
