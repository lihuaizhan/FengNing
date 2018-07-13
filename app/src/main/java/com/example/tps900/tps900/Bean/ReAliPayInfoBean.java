package com.example.tps900.tps900.Bean;

/**
 * 项目名称：PDA_CCHZL
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-04-23 16:47
 * 修改人：zxh
 * 修改时间：2018-04-23 16:47
 * 修改备注：
 */

public class ReAliPayInfoBean {

    /**
     * alipay_trade_query_response : {"code":"10000","msg":"Success","buyer_logon_id":"152****2643","buyer_pay_amount":"0.00","buyer_user_id":"2088812845542223","invoice_amount":"0.00","out_trade_no":"6560785976","point_amount":"0.00","receipt_amount":"0.00","send_pay_date":"2018-04-23 16:46:35","total_amount":"1.00","trade_no":"2018042321001004220590144624","trade_status":"TRADE_CLOSED"}
     * sign : NPWSa2TPlc4jPIbnrm8wFjjH3qZfT+2oN/xkdKcZsBNF88/8fiGBiWhrCLGwbkYFFoYluYXybAKVUnW6Cy89et/u8RArcVzWx8SmMd7l1JCOVelhwt+Tq12EYBxMEbYY/iBfXDT916lU8GMLNH+8JwXMXoGxT6WsLAOIA2pONO8=
     */

    public AlipayTradeQueryResponseBean alipay_trade_query_response;
    public String sign;

    public static class AlipayTradeQueryResponseBean {
        /**
         * code : 10000
         * msg : Success
         * buyer_logon_id : 152****2643
         * buyer_pay_amount : 0.00
         * buyer_user_id : 2088812845542223
         * invoice_amount : 0.00
         * out_trade_no : 6560785976
         * point_amount : 0.00
         * receipt_amount : 0.00
         * send_pay_date : 2018-04-23 16:46:35
         * total_amount : 1.00
         * trade_no : 2018042321001004220590144624
         * trade_status : TRADE_CLOSED
         */

        public String code;
        public String msg;
        public String buyer_logon_id;
        public String buyer_pay_amount;
        public String buyer_user_id;
        public String invoice_amount;
        public String out_trade_no;
        public String point_amount;
        public String receipt_amount;
        public String send_pay_date;
        public String total_amount;
        public String trade_no;
        public String trade_status;

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

        public String getBuyer_pay_amount() {
            return buyer_pay_amount;
        }

        public void setBuyer_pay_amount(String buyer_pay_amount) {
            this.buyer_pay_amount = buyer_pay_amount;
        }

        public String getBuyer_user_id() {
            return buyer_user_id;
        }

        public void setBuyer_user_id(String buyer_user_id) {
            this.buyer_user_id = buyer_user_id;
        }

        public String getInvoice_amount() {
            return invoice_amount;
        }

        public void setInvoice_amount(String invoice_amount) {
            this.invoice_amount = invoice_amount;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getPoint_amount() {
            return point_amount;
        }

        public void setPoint_amount(String point_amount) {
            this.point_amount = point_amount;
        }

        public String getReceipt_amount() {
            return receipt_amount;
        }

        public void setReceipt_amount(String receipt_amount) {
            this.receipt_amount = receipt_amount;
        }

        public String getSend_pay_date() {
            return send_pay_date;
        }

        public void setSend_pay_date(String send_pay_date) {
            this.send_pay_date = send_pay_date;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getTrade_status() {
            return trade_status;
        }

        public void setTrade_status(String trade_status) {
            this.trade_status = trade_status;
        }
    }
}
