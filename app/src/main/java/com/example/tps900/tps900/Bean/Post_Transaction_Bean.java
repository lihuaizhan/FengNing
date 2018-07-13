package com.example.tps900.tps900.Bean;

/**
 * 项目名称：GTJTPS613
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/14 17:41
 * 修改人：zxh
 * 修改时间：2017/3/14 17:41
 * 修改备注：
 */

public class Post_Transaction_Bean {
    public boolean isCheck=false;
    public String OrderId;
    public String OrderType;
    public String CustName;
    public String CustPhone;
    public String CustCardNo;
    public String OperatorId;
    public String MachineId;
    public String ParkId;
    public String ShopCode;
    public String SDate;
    public String STime;
    public String ProductSum;
    public String ProductCount;
    public String ProductAmt;
    public String MemCode;
    public String MemLevel;
    public String DisRate;
    //门票信息
    public String QuoteId;
    public String ProductId;
    public String ProductName;
    public String productPrice;
    public String PlayDate;
    public String ProductNum;
    public String DiscountRate;
    public String DiscountAmt;
    public String ECode;
    public String EndPlayDate;
    public String ThirdNo;
    public String ProductType;
    public String TypeName;
    public String OrderFrom;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getOrderFrom() {
        return OrderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        OrderFrom = orderFrom;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getThirdNo() {
        return ThirdNo;
    }

    public void setThirdNo(String thirdNo) {
        ThirdNo = thirdNo;
    }

    public String getEndPlayDate() {
        return EndPlayDate;
    }

    public void setEndPlayDate(String endPlayDate) {
        EndPlayDate = endPlayDate;
    }

    public int  count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getQuoteId() {
        return QuoteId;
    }

    public void setQuoteId(String quoteId) {
        QuoteId = quoteId;
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
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getPlayDate() {
        return PlayDate;
    }

    public void setPlayDate(String playDate) {
        PlayDate = playDate;
    }

    public String getProductNum() {
        return ProductNum;
    }

    public void setProductNum(String productNum) {
        ProductNum = productNum;
    }

    public String getDiscountRate() {
        return DiscountRate;
    }

    public void setDiscountRate(String discountRate) {
        DiscountRate = discountRate;
    }

    public String getDiscountAmt() {
        return DiscountAmt;
    }

    public void setDiscountAmt(String discountAmt) {
        DiscountAmt = discountAmt;
    }

    public String getECode() {
        return ECode;
    }

    public void setECode(String ECode) {
        this.ECode = ECode;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
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

    public String getCustCardNo() {
        return CustCardNo;
    }

    public void setCustCardNo(String custCardNo) {
        CustCardNo = custCardNo;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String operatorId) {
        OperatorId = operatorId;
    }

    public String getMachineId() {
        return MachineId;
    }

    public void setMachineId(String machineId) {
        MachineId = machineId;
    }

    public String getParkId() {
        return ParkId;
    }

    public void setParkId(String parkId) {
        ParkId = parkId;
    }

    public String getShopCode() {
        return ShopCode;
    }

    public void setShopCode(String shopCode) {
        ShopCode = shopCode;
    }

    public String getSDate() {
        return SDate;
    }

    public void setSDate(String SDate) {
        this.SDate = SDate;
    }

    public String getSTime() {
        return STime;
    }

    public void setSTime(String STime) {
        this.STime = STime;
    }

    public String getProductSum() {
        return ProductSum;
    }

    public void setProductSum(String productSum) {
        ProductSum = productSum;
    }

    public String getProductCount() {
        return ProductCount;
    }

    public void setProductCount(String productCount) {
        ProductCount = productCount;
    }

    public String getProductAmt() {
        return ProductAmt;
    }

    public void setProductAmt(String productAmt) {
        ProductAmt = productAmt;
    }

    public String getMemCode() {
        return MemCode;
    }

    public void setMemCode(String memCode) {
        MemCode = memCode;
    }

    public String getMemLevel() {
        return MemLevel;
    }

    public void setMemLevel(String memLevel) {
        MemLevel = memLevel;
    }

    public String getDisRate() {
        return DisRate;
    }

    public void setDisRate(String disRate) {
        DisRate = disRate;
    }
}
