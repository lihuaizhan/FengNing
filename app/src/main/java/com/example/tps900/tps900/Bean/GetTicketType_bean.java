package com.example.tps900.tps900.Bean;

import java.util.List;

/**
 * 项目名称：TPS613_WesternBrigade
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/3 17:06
 * 修改人：zxh
 * 修改时间：2017/3/3 17:06
 * 修改备注：
 */

public class GetTicketType_bean {


    /**
     * Data : [{"TypeId":"00000083","TypeName":"充值","ProductType":"8"},{"TypeId":"00000074","TypeName":"门票","ProductType":"1"},{"TypeId":"00000081","TypeName":"联票","ProductType":"1"}]
     * Success : true
     * Message : null
     */

    public boolean Success;
    public String  Message;
    public List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> data) {
        Data = data;
    }

    public static class DataBean {
        /**
         * TypeId : 00000083
         * TypeName : 充值
         * ProductType : 8
         */

        public String TypeId;
        public String TypeName;
        public String ProductType;

        public DataBean(String typeName) {
            TypeName = typeName;
        }

        public DataBean() {

        }


        public String getTypeId() {
            return TypeId;
        }

        public void setTypeId(String typeId) {
            TypeId = typeId;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String typeName) {
            TypeName = typeName;
        }

        public String getProductType() {
            return ProductType;
        }

        public void setProductType(String productType) {
            ProductType = productType;
        }
    }
}
