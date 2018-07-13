package com.example.tps900.tps900.MenberBean;

/**
 * 项目名称：PDA_FengNing
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-06-23 17:04
 * 修改人：zxh
 * 修改时间：2018-06-23 17:04
 * 修改备注：
 */

public class ReMemberConsumption {

    /**
     * status : 0
     * message : 消费成功！
     * billNumber : XF20140524000015
     * orderNumber :
     * availablePoint : 999
     * availableValue : 888
     */

    public int status;
    public String message;
    public String billNumber;
    public String orderNumber;
    public int availablePoint;
    public int availableValue;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getAvailablePoint() {
        return availablePoint;
    }

    public void setAvailablePoint(int availablePoint) {
        this.availablePoint = availablePoint;
    }

    public int getAvailableValue() {
        return availableValue;
    }

    public void setAvailableValue(int availableValue) {
        this.availableValue = availableValue;
    }
}
