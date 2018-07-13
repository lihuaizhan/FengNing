package com.example.tps900.tps900.Bean;

/**
 * Created by Administrator on 2018/7/12.
 */

public class VipApilyBean {

    /**
     * succeed : true
     * billNumber : XF20180712000004
     * msg : 成功
     */

    private boolean succeed;
    private String billNumber;
    private String msg;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
