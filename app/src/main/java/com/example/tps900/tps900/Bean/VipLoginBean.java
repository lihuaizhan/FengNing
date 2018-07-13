package com.example.tps900.tps900.Bean;

/**
 * Created by Administrator on 2018/7/12.
 */

public class VipLoginBean {

    /**
     * DiscountModel : {"CardId":"031500005535","EnablePoint":"296.52","Mobile":"18531433897","Pwd":"123456","TrueName":"测试用"}
     * msg : 成功
     * succeed : true
     */

    private DiscountModelBean DiscountModel;
    private String msg;
    private boolean succeed;

    public DiscountModelBean getDiscountModel() {
        return DiscountModel;
    }

    public void setDiscountModel(DiscountModelBean DiscountModel) {
        this.DiscountModel = DiscountModel;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public static class DiscountModelBean {
        /**
         * CardId : 031500005535
         * EnablePoint : 296.52
         * Mobile : 18531433897
         * Pwd : 123456
         * TrueName : 测试用
         */

        private String CardId;
        private String EnablePoint;
        private String Mobile;
        private String Pwd;
        private String TrueName;

        public String getCardId() {
            return CardId;
        }

        public void setCardId(String CardId) {
            this.CardId = CardId;
        }

        public String getEnablePoint() {
            return EnablePoint;
        }

        public void setEnablePoint(String EnablePoint) {
            this.EnablePoint = EnablePoint;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getPwd() {
            return Pwd;
        }

        public void setPwd(String Pwd) {
            this.Pwd = Pwd;
        }

        public String getTrueName() {
            return TrueName;
        }

        public void setTrueName(String TrueName) {
            this.TrueName = TrueName;
        }
    }
}
