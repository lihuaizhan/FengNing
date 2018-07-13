package com.example.tps900.tps900.Bean;

/**
 * 项目名称：PDA_DJY
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-04-25 16:14
 * 修改人：zxh
 * 修改时间：2018-04-25 16:14
 * 修改备注：
 */

public class TicketPrintBean {
    String printTime;
    String ticketName;
    String ticketPrice;
    String ticketCount;
    String verifNum;
    String startTime;
    String endTime;
    String Ecode;

    public TicketPrintBean(String printTime, String ticketName, String ticketPrice, String ticketCount, String verifNum, String startTime, String endTime, String ecode) {
        this.printTime = printTime;
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
        this.ticketCount = ticketCount;
        this.verifNum = verifNum;
        this.startTime = startTime;
        this.endTime = endTime;
        Ecode = ecode;
    }

    public String getEcode() {
        return Ecode;
    }

    public void setEcode(String ecode) {
        Ecode = ecode;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(String ticketCount) {
        this.ticketCount = ticketCount;
    }

    public String getVerifNum() {
        return verifNum;
    }

    public void setVerifNum(String verifNum) {
        this.verifNum = verifNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
