package com.example.tps900.tps900.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：TPS613_WesternBrigade
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/3 17:39
 * 修改人：zxh
 * 修改时间：2017/3/3 17:39
 * 修改备注：
 */
public class GetTicket_Bean implements Serializable {

    /**
     * Data : [{"ProductId":"00000646","ProductName":"羊湖成人票","ProductPrice":90,"ProductCode":null,"ProductType":"1","PrintName":"羊湖成人票","PrintPrice":100,"Remark":null,"QuoteId":"00000085","TypeId":"00000074"},{"ProductId":"00000647","ProductName":"琼结","ProductPrice":90,"ProductCode":null,"ProductType":"1","PrintName":"琼结成人票","PrintPrice":100,"Remark":null,"QuoteId":"00000085","TypeId":"00000074"},{"ProductId":"00000648","ProductName":"琼结+羊湖+尼木吞巴成人单人联票","ProductPrice":270,"ProductCode":null,"ProductType":"1","PrintName":"琼结+羊湖+尼木吞巴成人单人联票","PrintPrice":300,"Remark":null,"QuoteId":"00000085","TypeId":"00000081"},{"ProductId":"00000652","ProductName":"琼结成人票+琼结小木屋","ProductPrice":290,"ProductCode":null,"ProductType":"1","PrintName":"琼结成人票+琼结小木屋","PrintPrice":300,"Remark":null,"QuoteId":"00000085","TypeId":"00000081"},{"ProductId":"00000654","ProductName":"冲100","ProductPrice":0.01,"ProductCode":null,"ProductType":"8","PrintName":null,"PrintPrice":0,"Remark":null,"QuoteId":"00000085","TypeId":"00000083"},{"ProductId":"00000655","ProductName":"冲100送50","ProductPrice":0.01,"ProductCode":null,"ProductType":"8","PrintName":null,"PrintPrice":0,"Remark":null,"QuoteId":"00000085","TypeId":"00000083"}]
     * Success : true
     * Message : null
     */

    public boolean Success;
    public String Message;
    public List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> data) {
        Data = data;
    }

    public static class DataBean implements Serializable {

        /**
         * ProductId : 00000646
         * ProductName : 羊湖成人票
         * ProductPrice : 90
         * ProductCode : null
         * ProductType : 1
         * PrintName : 羊湖成人票
         * PrintPrice : 100
         * Remark : null
         * QuoteId : 00000085
         * TypeId : 00000074
         */
        public String OrderID;

        public String getOrderID() {
            return OrderID;
        }

        public void setOrderID(String orderID) {
            OrderID = orderID;
        }

        public int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String printCount;
        public String printCountPrice;
        public String ProductId;
        public String ProductName;
        public String ProductPrice;
        public String ProductCode;
        public String ProductType;
        public String PrintName;
        public String PrintPrice;
        public String Remark;
        public String QuoteId;
        public String TypeId;
        public int ticketnumber;
        public String countPrice;
        public String Pay_Code;
        public String Pay_Money;
        public String TypeName;

        private String printingNumber;
        private String physicalNumber;
        private String date;
        private String cardLevel;
        private String rechargeType;
        private String cardType;
        private String vipId;
        private String cardLevelId;
        private String cardNO;
        private String deposit;
        private String rent;


        private String vipName;
        private String vipSex;
        private String vipPhone;
        private String vipAddress;
        private String vipCertificatesNo;
        private String vipBirthday;
        private String balance;
        private String gift;

        private String levelName;
        private String isStored;
        private String isVipMark;
        private String isAdJust;
        private double depositAmount;
        private String depositItid;
        private double rentAmount;
        private String rentItid;
        private double reissueAmount;
        private String reissueItid;
        private String returnItId;

        private int[] headPhoto;
        private String picUrl;
        private int[] fingurePhoto;
        private String fingureUrl;
        private String Ecode;
        private String STime;
        private String ETime;
        public void setSTime(String STime) {
            this.STime = STime;
        }

        public void setETime(String ETime) {
            this.ETime = ETime;
        }

        public String getETime() {
            return ETime;
        }

        public String getSTime() {
            return STime;
        }

        public String getEcode() {
            return Ecode;
        }

        public void setEcode(String ecode) {
            Ecode = ecode;
        }

        public void setFingurePhoto(int[] fingurePhoto) {
            this.fingurePhoto = fingurePhoto;
        }

        public void setFingureUrl(String fingureUrl) {
            this.fingureUrl = fingureUrl;
        }

        public int[] getFingurePhoto() {
            return fingurePhoto;
        }

        public String getFingureUrl() {
            return fingureUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setHeadPhoto(int[] headPhoto) {
            this.headPhoto = headPhoto;
        }

        public int[] getHeadPhoto() {
            return headPhoto;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String typeName) {
            TypeName = typeName;
        }

        public DataBean() {
        }

        public String getPrintCount() {
            return printCount;
        }

        public void setPrintCount(String printCount) {
            this.printCount = printCount;
        }

        public String getPrintCountPrice() {
            return printCountPrice;
        }

        public void setPrintCountPrice(String printCountPrice) {
            this.printCountPrice = printCountPrice;
        }

        public String getPay_Code() {
            return Pay_Code;
        }

        public void setPay_Code(String pay_Code) {
            Pay_Code = pay_Code;
        }

        public String getPay_Money() {
            return Pay_Money;
        }

        public void setPay_Money(String pay_Money) {
            Pay_Money = pay_Money;
        }

        public String getCountPrice() {
            return countPrice;
        }

        public void setCountPrice(String countPrice) {
            this.countPrice = countPrice;
        }

        public int getTicketnumber() {
            return ticketnumber;
        }

        public void setTicketnumber(int ticketnumber) {
            this.ticketnumber = ticketnumber;
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

        public String getProductPrice() {
            return ProductPrice;
        }

        public void setProductPrice(String productPrice) {
            ProductPrice = productPrice;
        }

        public String getProductCode() {
            return ProductCode;
        }

        public void setProductCode(String productCode) {
            ProductCode = productCode;
        }

        public String getProductType() {
            return ProductType;
        }

        public void setProductType(String productType) {
            ProductType = productType;
        }

        public String getPrintName() {
            return PrintName;
        }

        public void setPrintName(String printName) {
            PrintName = printName;
        }

        public String getPrintPrice() {
            return PrintPrice;
        }

        public void setPrintPrice(String printPrice) {
            PrintPrice = printPrice;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getQuoteId() {
            return QuoteId;
        }

        public void setQuoteId(String quoteId) {
            QuoteId = quoteId;
        }

        public String getTypeId() {
            return TypeId;
        }

        public void setTypeId(String typeId) {
            TypeId = typeId;
        }

        public void setRechargeType(String rechargeType) {
            this.rechargeType = rechargeType;
        }

        public void setCardLevel(String cardLevel) {
            this.cardLevel = cardLevel;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setPhysicalNumber(String physicalNumber) {
            this.physicalNumber = physicalNumber;
        }

        public void setPrintingNumber(String printingNumber) {
            this.printingNumber = printingNumber;
        }

        public String getRechargeType() {
            return rechargeType;
        }

        public String getCardLevel() {
            return cardLevel;
        }

        public String getDate() {
            return date;
        }

        public String getPhysicalNumber() {
            return physicalNumber;
        }

        public String getPrintingNumber() {
            return printingNumber;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardLevelId(String cardLevelId) {
            this.cardLevelId = cardLevelId;
        }

        public void setVipId(String vipId) {
            this.vipId = vipId;
        }

        public String getCardLevelId() {
            return cardLevelId;
        }

        public String getVipId() {
            return vipId;
        }


        public void setCardNO(String cardNO) {
            this.cardNO = cardNO;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public void setRent(String rent) {
            this.rent = rent;
        }

        public String getCardNO() {
            return cardNO;
        }

        public String getDeposit() {
            return deposit;
        }

        public String getRent() {
            return rent;
        }

        public void setVipAddress(String vipAddress) {
            this.vipAddress = vipAddress;
        }

        public void setVipBirthday(String vipBirthday) {
            this.vipBirthday = vipBirthday;
        }

        public void setVipCertificatesNo(String vipCertificatesNo) {
            this.vipCertificatesNo = vipCertificatesNo;
        }

        public void setVipName(String vipName) {
            this.vipName = vipName;
        }

        public void setVipPhone(String vipPhone) {
            this.vipPhone = vipPhone;
        }

        public void setVipSex(String vipSex) {
            this.vipSex = vipSex;
        }

        public String getVipAddress() {
            return vipAddress;
        }

        public String getVipBirthday() {
            return vipBirthday;
        }

        public String getVipCertificatesNo() {
            return vipCertificatesNo;
        }

        public String getVipName() {
            return vipName;
        }

        public String getVipPhone() {
            return vipPhone;
        }

        public String getVipSex() {
            return vipSex;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getBalance() {
            return balance;
        }

        public void setGift(String gift) {
            this.gift = gift;
        }

        public String getGift() {
            return gift;
        }

        public void setDepositAmount(double depositAmount) {
            this.depositAmount = depositAmount;
        }

        public void setDepositItid(String depositItid) {
            this.depositItid = depositItid;
        }

        public void setIsAdJust(String isAdJust) {
            this.isAdJust = isAdJust;
        }

        public void setIsStored(String isStored) {
            this.isStored = isStored;
        }

        public void setIsVipMark(String isVipMark) {
            this.isVipMark = isVipMark;
        }


        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public void setReissueAmount(double reissueAmount) {
            this.reissueAmount = reissueAmount;
        }

        public void setReissueItid(String reissueItid) {
            this.reissueItid = reissueItid;
        }

        public void setRentAmount(double rentAmount) {
            this.rentAmount = rentAmount;
        }

        public void setRentItid(String rentItid) {
            this.rentItid = rentItid;
        }

        public double getDepositAmount() {
            return depositAmount;
        }

        public String getDepositItid() {
            return depositItid;
        }

        public double getReissueAmount() {
            return reissueAmount;
        }

        public String getReissueItid() {
            return reissueItid;
        }

        public double getRentAmount() {
            return rentAmount;
        }

        public String getRentItid() {
            return rentItid;
        }

        public String getIsAdJust() {
            return isAdJust;
        }

        public String getIsStored() {
            return isStored;
        }

        public String getIsVipMark() {
            return isVipMark;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setReturnItId(String returnItId) {
            this.returnItId = returnItId;
        }

        public String getReturnItId() {
            return returnItId;
        }
    }


}
