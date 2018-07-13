package com.example.tps900.tps900.Bean;

import java.util.List;

/**
 * Created by zxh on 2016/12/17.
 * 微信支付解析返回结果
 * @author zxh
 */
public class Alipay_trade_query_response {

    public  String code;
    public  String msg;
    public  String buyer_logon_id;
    public  String buyer_pay_amount;
    public  String buyer_user_id;
    public  List<Fund_Bill_List> fund_bill_list;
    public  String invoice_amount;
    public  String open_id;
    public  String out_trade_no;
    public  String point_amount;
    public  String receipt_amount;
    public  String send_pay_date;
    public  String total_amount;
    public  String trade_no;
    public  String trade_status;

    public  String getCode() {
        return code;
    }

    public  void setCode(String code) {
        this.code = code;
    }

    public  String getMsg() {
        return msg;
    }

    public  void setMsg(String msg) {
        this.msg = msg;
    }

    public  String getBuyer_logon_id() {
        return buyer_logon_id;
    }

    public  void setBuyer_logon_id(String buyer_logon_id) {
        this.buyer_logon_id = buyer_logon_id;
    }

    public  String getBuyer_pay_amount() {
        return buyer_pay_amount;
    }

    public  void setBuyer_pay_amount(String buyer_pay_amount) {
        this.buyer_pay_amount = buyer_pay_amount;
    }

    public  String getBuyer_user_id() {
        return buyer_user_id;
    }

    public  void setBuyer_user_id(String buyer_user_id) {
        this.buyer_user_id = buyer_user_id;
    }


    public  String getInvoice_amount() {
        return invoice_amount;
    }

    public  void setInvoice_amount(String invoice_amount) {
        this.invoice_amount = invoice_amount;
    }

    public  String getOpen_id() {
        return open_id;
    }

    public  void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public  String getOut_trade_no() {
        return out_trade_no;
    }

    public  void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public  String getPoint_amount() {
        return point_amount;
    }

    public  void setPoint_amount(String point_amount) {
        this.point_amount = point_amount;
    }

    public  String getReceipt_amount() {
        return receipt_amount;
    }

    public  void setReceipt_amount(String receipt_amount) {
        this.receipt_amount = receipt_amount;
    }

    public  String getSend_pay_date() {
        return send_pay_date;
    }

    public  void setSend_pay_date(String send_pay_date) {
        this.send_pay_date = send_pay_date;
    }

    public  String getTotal_amount() {
        return total_amount;
    }

    public  void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public  String getTrade_no() {
        return trade_no;
    }

    public  void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public  String getTrade_status() {
        return trade_status;
    }

    public  void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public List<Fund_Bill_List> getFund_bill_list() {
        return fund_bill_list;
    }

    public void setFund_bill_list(List<Fund_Bill_List> fund_bill_list) {
        this.fund_bill_list = fund_bill_list;
    }
}
