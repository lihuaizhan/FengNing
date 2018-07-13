package com.example.tps900.tps900.MenberBean;

/**
 * 项目名称：PDA_FengNing
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-06-23 17:12
 * 修改人：zxh
 * 修改时间：2018-06-23 17:12
 * 修改备注：
 */

public class ReMemberOrderUndo {

    /**
     * status : 0
     * message : 撤销成功！
     * billNumber : TH20140524000015
     * availablePoint : 999
     * availableValue : 888
     */

    public int status;
    public String message;
    public String billNumber;
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
