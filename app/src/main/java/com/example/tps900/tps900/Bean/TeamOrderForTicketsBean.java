package com.example.tps900.tps900.Bean;

/**
 * 项目名称：PDA_CCHZL
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-04-23 15:57
 * 修改人：zxh
 * 修改时间：2018-04-23 15:57
 * 修改备注：
 */

public class TeamOrderForTicketsBean {

    /**
     * Success : false
     * Code : null
     * Message : ORA-01008: 并非所有变量都已绑定

     * dtTime : null
     * Orderno : null
     */

    public boolean Success;
    public String Code;
    public String Message;
    public String dtTime;
    public String Orderno;

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
}
