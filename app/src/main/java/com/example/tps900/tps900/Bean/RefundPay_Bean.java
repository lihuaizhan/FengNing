package com.example.tps900.tps900.Bean;

import java.util.List;

/**
 * 项目名称：GTJTPS613
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/20 17:07
 * 修改人：zxh
 * 修改时间：2017/3/20 17:07
 * 修改备注：
 */

public class RefundPay_Bean {

    /**
     * alipay_trade_refund_response : {"code":"10000","msg":"Success","buyer_logon_id":"137****0604","buyer_user_id":"2088802198219543","fund_change":"Y","gmt_refund_pay":"2017-03-20 14:01:42","open_id":"20880039268193704297644202215354","out_trade_no":"1733","refund_detail_item_list":[{"amount":"0.01","fund_channel":"PCREDIT"}],"refund_fee":"0.01","send_back_fee":"0.01","trade_no":"2017032021001004540249112082"}
     * sign : lR+GI8aN9YDCzeGEQqx4SkFY4gPv04HlhrB9oH/tJwYW9xNZMNa4Z77RAF02xHf1a7U3bTTBofiLam+c9i2I/8669fVoo+IQU6hQELvheA8T6Q2H0YQ0ruYUBfkjcopsTc9tBNQbh2T8SKK8gYOGfHGapf038yhOAHrUsxmddeQ=
     */

    public AlipayTradeRefundResponseBean alipay_trade_refund_response;
    public String sign;

    public AlipayTradeRefundResponseBean getAlipay_trade_refund_response() {
        return alipay_trade_refund_response;
    }

    public void setAlipay_trade_refund_response(AlipayTradeRefundResponseBean alipay_trade_refund_response) {
        this.alipay_trade_refund_response = alipay_trade_refund_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class AlipayTradeRefundResponseBean {
        /**
         * code : 10000
         * msg : Success
         * buyer_logon_id : 137****0604
         * buyer_user_id : 2088802198219543
         * fund_change : Y
         * gmt_refund_pay : 2017-03-20 14:01:42
         * open_id : 20880039268193704297644202215354
         * out_trade_no : 1733
         * refund_detail_item_list : [{"amount":"0.01","fund_channel":"PCREDIT"}]
         * refund_fee : 0.01
         * send_back_fee : 0.01
         * trade_no : 2017032021001004540249112082
         */

        public String code;
        public String msg;
        public String buyer_logon_id;
        public String buyer_user_id;
        public String fund_change;
        public String gmt_refund_pay;
        public String open_id;
        public String out_trade_no;
        public String refund_fee;
        public String send_back_fee;
        public String trade_no;
        public List<RefundDetailItemListBean> refund_detail_item_list;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getBuyer_logon_id() {
            return buyer_logon_id;
        }

        public void setBuyer_logon_id(String buyer_logon_id) {
            this.buyer_logon_id = buyer_logon_id;
        }

        public String getBuyer_user_id() {
            return buyer_user_id;
        }

        public void setBuyer_user_id(String buyer_user_id) {
            this.buyer_user_id = buyer_user_id;
        }

        public String getFund_change() {
            return fund_change;
        }

        public void setFund_change(String fund_change) {
            this.fund_change = fund_change;
        }

        public String getGmt_refund_pay() {
            return gmt_refund_pay;
        }

        public void setGmt_refund_pay(String gmt_refund_pay) {
            this.gmt_refund_pay = gmt_refund_pay;
        }

        public String getOpen_id() {
            return open_id;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getRefund_fee() {
            return refund_fee;
        }

        public void setRefund_fee(String refund_fee) {
            this.refund_fee = refund_fee;
        }

        public String getSend_back_fee() {
            return send_back_fee;
        }

        public void setSend_back_fee(String send_back_fee) {
            this.send_back_fee = send_back_fee;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public List<RefundDetailItemListBean> getRefund_detail_item_list() {
            return refund_detail_item_list;
        }

        public void setRefund_detail_item_list(List<RefundDetailItemListBean> refund_detail_item_list) {
            this.refund_detail_item_list = refund_detail_item_list;
        }

        public static class RefundDetailItemListBean {
            /**
             * amount : 0.01
             * fund_channel : PCREDIT
             */

            public String amount;
            public String fund_channel;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getFund_channel() {
                return fund_channel;
            }

            public void setFund_channel(String fund_channel) {
                this.fund_channel = fund_channel;
            }
        }
    }
}
