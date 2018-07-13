package com.example.tps900.tps900.Bean;

import java.util.List;

/**
 * 项目名称：GTJTPS613
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/13 13:38
 * 修改人：zxh
 * 修改时间：2017/3/13 13:38
 * 修改备注：
 */

public class PayOrder_Bean {

    /**
     * Data : [{"OrderId":"PFS00000627","OrderDlId":"PFS0000062700001","ProductId":"00000647","Ecode":"166270696108","StartPlayDate":"2017-03-15 12:00:00","EndPlayDate":"2017-03-16 12:00:00"}]
     * Success : true
     * Message : null
     */

    public boolean Success;
    public Object Message;
    public List<DataBean> Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object message) {
        Message = message;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> data) {
        Data = data;
    }

    public static class DataBean {
        /**
         * OrderId : PFS00000627
         * OrderDlId : PFS0000062700001
         * ProductId : 00000647
         * Ecode : 166270696108
         * StartPlayDate : 2017-03-15 12:00:00
         * EndPlayDate : 2017-03-16 12:00:00
         */

        public String OrderId;
        public String OrderDlId;
        public String ProductId;
        public String Ecode;
        public String StartPlayDate;
        public String EndPlayDate;

        public String getOrderId() {
            return OrderId;
        }

        public void setOrderId(String orderId) {
            OrderId = orderId;
        }

        public String getOrderDlId() {
            return OrderDlId;
        }

        public void setOrderDlId(String orderDlId) {
            OrderDlId = orderDlId;
        }

        public String getProductId() {
            return ProductId;
        }

        public void setProductId(String productId) {
            ProductId = productId;
        }

        public String getEcode() {
            return Ecode;
        }

        public void setEcode(String ecode) {
            Ecode = ecode;
        }

        public String getStartPlayDate() {
            return StartPlayDate;
        }

        public void setStartPlayDate(String startPlayDate) {
            StartPlayDate = startPlayDate;
        }

        public String getEndPlayDate() {
            return EndPlayDate;
        }

        public void setEndPlayDate(String endPlayDate) {
            EndPlayDate = endPlayDate;
        }
    }
}
