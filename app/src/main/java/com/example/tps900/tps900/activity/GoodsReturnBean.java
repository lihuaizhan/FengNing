package com.example.tps900.tps900.activity;

/**
 * 项目名称：PDA_FuHua
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-04-03 18:42
 * 修改人：zxh
 * 修改时间：2018-04-03 18:42
 * 修改备注：
 */

public class GoodsReturnBean {

    /**
     * isSuccess : true
     * error : null
     * ORDERNO :
     * RETRIEVALNO : 76946080
     */

    public boolean isSuccess;
    public String  error;
    public String ORDERNO;
    public String RETRIEVALNO;

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

    public String getORDERNO() {
        return ORDERNO;
    }

    public void setORDERNO(String ORDERNO) {
        this.ORDERNO = ORDERNO;
    }

    public String getRETRIEVALNO() {
        return RETRIEVALNO;
    }

    public void setRETRIEVALNO(String RETRIEVALNO) {
        this.RETRIEVALNO = RETRIEVALNO;
    }
}
