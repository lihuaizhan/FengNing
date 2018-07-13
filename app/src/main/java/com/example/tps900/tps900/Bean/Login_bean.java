package com.example.tps900.tps900.Bean;

import java.util.List;

/**
 * 项目名称：TPS613_WesternBrigade
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/1 14:59
 * 修改人：zxh
 * 修改时间：2017/3/1 14:59
 * 修改备注：
 */

public class Login_bean {


    /**
     * Data : {"ParkId":"00000205","ParkName":"恒大海花岛乐园群","MachineId":"00000110","MachineName":"核销款台2","PrintTitle":"吉野家嘉华大厦店","PrintEnd":"这店有毒","ShopCode":"00000333","ShopName":"陆地公园","EmployeeCode":"00000289","EmployeeName":"梦幻世界管理员","PayType":[{"TypeCode":"005","TypeName":"一卡通"},{"TypeCode":"006","TypeName":"账户"},{"TypeCode":"001","TypeName":"微信"}]}
     * Success : true
     * Message : null
     */

    public DataBean Data;
    public boolean Success;
    public String  Message;

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
         * ParkId : 00000205
         * ParkName : 恒大海花岛乐园群
         * MachineId : 00000110
         * MachineName : 核销款台2
         * PrintTitle : 吉野家嘉华大厦店
         * PrintEnd : 这店有毒
         * ShopCode : 00000333
         * ShopName : 陆地公园
         * EmployeeCode : 00000289
         * EmployeeName : 梦幻世界管理员
         * PayType : [{"TypeCode":"005","TypeName":"一卡通"},{"TypeCode":"006","TypeName":"账户"},{"TypeCode":"001","TypeName":"微信"}]
         */
        public String ParkId;
        public String ParkName;
        public String MachineId;
        public String MachineName;
        public String PrintTitle;
        public String PrintEnd;
        public String ShopCode;
        public String ShopName;
        public String EmployeeCode;
        public String EmployeeName;
        public List<PayTypeBean> PayType;

        public String getParkId() {
            return ParkId;
        }

        public void setParkId(String parkId) {
            ParkId = parkId;
        }

        public String getParkName() {
            return ParkName;
        }

        public void setParkName(String parkName) {
            ParkName = parkName;
        }

        public String getMachineId() {
            return MachineId;
        }

        public void setMachineId(String machineId) {
            MachineId = machineId;
        }

        public String getMachineName() {
            return MachineName;
        }

        public void setMachineName(String machineName) {
            MachineName = machineName;
        }

        public String getPrintTitle() {
            return PrintTitle;
        }

        public void setPrintTitle(String printTitle) {
            PrintTitle = printTitle;
        }

        public String getPrintEnd() {
            return PrintEnd;
        }

        public void setPrintEnd(String printEnd) {
            PrintEnd = printEnd;
        }

        public String getShopCode() {
            return ShopCode;
        }

        public void setShopCode(String shopCode) {
            ShopCode = shopCode;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String shopName) {
            ShopName = shopName;
        }

        public String getEmployeeCode() {
            return EmployeeCode;
        }

        public void setEmployeeCode(String employeeCode) {
            EmployeeCode = employeeCode;
        }

        public String getEmployeeName() {
            return EmployeeName;
        }

        public void setEmployeeName(String employeeName) {
            EmployeeName = employeeName;
        }

        public List<PayTypeBean> getPayType() {
            return PayType;
        }

        public void setPayType(List<PayTypeBean> payType) {
            PayType = payType;
        }

        public static class PayTypeBean {
            /**
             * TypeCode : 005
             * TypeName : 一卡通
             */

            public String TypeCode;
            public String TypeName;
            public String getTypeCode() {
                return TypeCode;
            }

            public void setTypeCode(String typeCode) {
                TypeCode = typeCode;
            }

            public String getTypeName() {
                return TypeName;
            }

            public void setTypeName(String typeName) {
                TypeName = typeName;
            }
        }
    }
}
