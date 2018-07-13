package com.example.tps900.tps900.InterFace;

import android.content.Intent;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-03-09 15:38
 * 修改人：zxh
 * 修改时间：2018-03-09 15:38
 * 修改备注：
 */

public class MessageEvent {
    public int requestCode;
    public int resultCode;
    public Intent data;
    public String ecode;
    public int ReallyPeople;

    public MessageEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public MessageEvent(int requestCode, String ecode, int reallyPeople) {
        this.requestCode = requestCode;
        this.ecode = ecode;
        ReallyPeople = reallyPeople;
    }

    public MessageEvent(String msg) {
        this.ecode = msg;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public int getReallyPeople() {
        return ReallyPeople;
    }

    public void setReallyPeople(int reallyPeople) {
        ReallyPeople = reallyPeople;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }
}
