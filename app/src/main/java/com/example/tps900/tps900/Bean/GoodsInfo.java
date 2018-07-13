package com.example.tps900.tps900.Bean;

import java.io.Serializable;

/**
 * 商品信息
 */
public class GoodsInfo implements Serializable {
    protected String Id;
    protected boolean isChoosed;
    /**
     * OrderNo : F00003394
     * OrderId : 00003394
     * ProductId : 00000890
     * ProductName : 水世界成人票
     * ProductCount : 2
     * ProductPrice : 11
     * OrderAmt : 22
     * PlayStartDate : 2017-8-1 0:00:00
     * PlayEndDate : 2017-10-29 23:59:59
     * Ecode : 163887506764
     * CustName : 123456
     * CustPhone : 15232842469
     * PrintPrice : 0.10
     * PrintName : 水世界成人票
     * CardNo : 123456789012345678
     * Stime : null
     * Etime : null
     * ReturnNum : 0
     * AvailableNum : 2
     * Incount : 0
     * IsPrint : 0
     * OffCode : null
     * Paytime : 2017-08-01 09:38:57
     * Yftime : 20
     */

    public String OrderNo;
    public String OrderId;
    public String ProductId;
    public String ProductName;
    public String ProductCount;
    public double ProductPrice;
    public double OrderAmt;
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
    public String Paytime;
    public String Yftime;
    public String remark;
    public GoodsInfo(String id, String orderNo, String productId, String productName, String productCount, double productPrice, String playStartDate, String playEndDate, String ecode) {
        Id = id;
        OrderNo = orderNo;
        ProductId = productId;
        ProductName = productName;
        ProductCount = productCount;
        ProductPrice = productPrice;
        PlayStartDate = playStartDate;
        PlayEndDate = playEndDate;
        Ecode = ecode;
    }

    public GoodsInfo() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
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

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public double getOrderAmt() {
        return OrderAmt;
    }

    public void setOrderAmt(double orderAmt) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
