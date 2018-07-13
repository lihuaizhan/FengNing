package com.example.tps900.tps900.Bean;

/**
 * 项目名称：GTJTPS613
 * 类名称：CreateOrder_Bean
 * 类描述：创建订单
 * 创建人：zxh
 * 创建时间：2017/3/13 11:05
 * 修改人：zxh
 * 修改时间：2017/3/13 11:05
 * 修改备注：
 */

public class CreateOrder_Bean {


    /**
     * Data : {"OrderId":"PFS00000620"}
     * Success : true
     * Message : null
     */

    public DataBean Data;
    public boolean Success;
    public String Message;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean data) {
        Data = data;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public static class DataBean {
        /**
         * OrderId : PFS00000620
         */

        public String OrderId;

        public String getOrderId() {
            return OrderId;
        }

        public void setOrderId(String orderId) {
            OrderId = orderId;
        }
    }
}
