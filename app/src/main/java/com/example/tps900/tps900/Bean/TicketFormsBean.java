package com.example.tps900.tps900.Bean;



/**
 * 项目名称：T_S_TPS900_AFK
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/6/15 13:51
 * 修改人：zxh
 * 修改时间：2017/6/15 13:51
 * 修改备注：
 */

public class TicketFormsBean {
    /**
     * 2. 票务销售报表：
     COMPANY --- 公司             PARK --- 景区          GZONE --- 营业点              TERMINALID --- 销售终端
     DEALID --- 交易号            NOTE --- 备注           PROMISSORYNOTE --- 是否期票  DEALDATE --- 销售日期
     DEALTIME --- 销售时间        DEALNAME --- 销售员     PAYMENTSTYLE --- 付款方式
     PAYMENTDETAIL --- 支付明细  ITEMNAME --- 项目名称 BASICTYPE --- 基本类型
     ITEMMONEY --- 项目价格  AMOUNT --- 数量  MONEY --- 金额 PEOPLECOUNT --- 人数  ACCOUNTSID --- 结算编号
     TYPEONE --- 类型一    TYPETWO --- 类型二  TYPETHREE --- 类型三  TYPEFOUR --- 类型四  TYPEFIVE --- 类型五  TYPESIX ---

     */
    /**
     * COMPANY : 北京xxx
     * PARK : 欢乐时光
     * GZONE : TVM 售票点
     * TERMINALID : TVM01
     * DEALID : 3132
     * DEALDATE : 2017-06-15
     * DEALTIME : 09:38:04
     * DEALNAME : TVM01
     * TICKETNAME : TVM成人票
     * BARCODE : 170615000000000
     * TICKETMONEY : 100.00
     * STARTDATE : 2017-06-15
     * ENDDATE : 2017-06-15
     * STARTTIME : 00:00:00
     * ENDTIME : 23:59:59
     * INPARKCOUNT : 1
     * INPARKSTYLE : 门票
     * UPDATESTATE : 未入满
     * INPARKTIMESMAX : 96
     * INPARKNAME : 欢乐时光
     * INPARKTIMES : 96
     * INPARK : 10
     */

    public String COMPANY;
    public String PARK;
    public String GZONE;
    public String TERMINALID;
    public String DEALID;
    public String DEALDATE;
    public String DEALTIME;
    public String DEALNAME;
    public String TICKETNAME;
    public String BARCODE;
    public String TICKETMONEY;
    public String STARTDATE;
    public String ENDDATE;
    public String STARTTIME;
    public String ENDTIME;
    public String INPARKCOUNT;
    public String INPARKSTYLE;
    public String UPDATESTATE;
    public String INPARKTIMESMAX;
    public String INPARKNAME;
    public String INPARKTIMES;
    public String INPARK;

    public String getCOMPANY() {
        return COMPANY;
    }

    public void setCOMPANY(String COMPANY) {
        this.COMPANY = COMPANY;
    }

    public String getPARK() {
        return PARK;
    }

    public void setPARK(String PARK) {
        this.PARK = PARK;
    }

    public String getGZONE() {
        return GZONE;
    }

    public void setGZONE(String GZONE) {
        this.GZONE = GZONE;
    }

    public String getTERMINALID() {
        return TERMINALID;
    }

    public void setTERMINALID(String TERMINALID) {
        this.TERMINALID = TERMINALID;
    }

    public String getDEALID() {
        return DEALID;
    }

    public void setDEALID(String DEALID) {
        this.DEALID = DEALID;
    }

    public String getDEALDATE() {
        return DEALDATE;
    }

    public void setDEALDATE(String DEALDATE) {
        this.DEALDATE = DEALDATE;
    }

    public String getDEALTIME() {
        return DEALTIME;
    }

    public void setDEALTIME(String DEALTIME) {
        this.DEALTIME = DEALTIME;
    }

    public String getDEALNAME() {
        return DEALNAME;
    }

    public void setDEALNAME(String DEALNAME) {
        this.DEALNAME = DEALNAME;
    }

    public String getTICKETNAME() {
        return TICKETNAME;
    }

    public void setTICKETNAME(String TICKETNAME) {
        this.TICKETNAME = TICKETNAME;
    }

    public String getBARCODE() {
        return BARCODE;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }

    public String getTICKETMONEY() {
        return TICKETMONEY;
    }

    public void setTICKETMONEY(String TICKETMONEY) {
        this.TICKETMONEY = TICKETMONEY;
    }

    public String getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public String getENDDATE() {
        return ENDDATE;
    }

    public void setENDDATE(String ENDDATE) {
        this.ENDDATE = ENDDATE;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public String getINPARKCOUNT() {
        return INPARKCOUNT;
    }

    public void setINPARKCOUNT(String INPARKCOUNT) {
        this.INPARKCOUNT = INPARKCOUNT;
    }

    public String getINPARKSTYLE() {
        return INPARKSTYLE;
    }

    public void setINPARKSTYLE(String INPARKSTYLE) {
        this.INPARKSTYLE = INPARKSTYLE;
    }

    public String getUPDATESTATE() {
        return UPDATESTATE;
    }

    public void setUPDATESTATE(String UPDATESTATE) {
        this.UPDATESTATE = UPDATESTATE;
    }

    public String getINPARKTIMESMAX() {
        return INPARKTIMESMAX;
    }

    public void setINPARKTIMESMAX(String INPARKTIMESMAX) {
        this.INPARKTIMESMAX = INPARKTIMESMAX;
    }

    public String getINPARKNAME() {
        return INPARKNAME;
    }

    public void setINPARKNAME(String INPARKNAME) {
        this.INPARKNAME = INPARKNAME;
    }

    public String getINPARKTIMES() {
        return INPARKTIMES;
    }

    public void setINPARKTIMES(String INPARKTIMES) {
        this.INPARKTIMES = INPARKTIMES;
    }

    public String getINPARK() {
        return INPARK;
    }

    public void setINPARK(String INPARK) {
        this.INPARK = INPARK;
    }
}
