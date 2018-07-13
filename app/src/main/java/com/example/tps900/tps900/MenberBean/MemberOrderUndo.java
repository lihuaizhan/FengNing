package com.example.tps900.tps900.MenberBean;

import java.util.List;

/**
 * 项目名称：PDA_FengNing
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-06-23 17:02
 * 修改人：zxh
 * 修改时间：2018-06-23 17:02
 * 修改备注：
 */

public class MemberOrderUndo {

    /**
     * userAccount : 10000
     * billNumber : XF20160812000006
     * meno : 衣服有点大
     * returnItems : [{"ReturnNumber":1,"GoodsItemGuid":"17acd061-fe55-e611-a1d6-7c0507895043","Guid":"cc98a421-2c60-e611-881c-7c0507895043"}]
     */

    public String userAccount;
    public String billNumber;
    public String meno;
    public List<ReturnItemsBean> returnItems;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public List<ReturnItemsBean> getReturnItems() {
        return returnItems;
    }

    public void setReturnItems(List<ReturnItemsBean> returnItems) {
        this.returnItems = returnItems;
    }

    public static class ReturnItemsBean {
        /**
         * ReturnNumber : 1
         * GoodsItemGuid : 17acd061-fe55-e611-a1d6-7c0507895043
         * Guid : cc98a421-2c60-e611-881c-7c0507895043
         */

        public int ReturnNumber;
        public String GoodsItemGuid;
        public String Guid;

        public int getReturnNumber() {
            return ReturnNumber;
        }

        public void setReturnNumber(int returnNumber) {
            ReturnNumber = returnNumber;
        }
    }
}
