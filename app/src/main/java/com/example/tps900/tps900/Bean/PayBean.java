package com.example.tps900.tps900.Bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by wo on 2017/5/3.
 */

public class PayBean {


    /**
     * dt : [{"NPAYMENTTYPE":1,"NID":1076,"SMONEYNAMECN":"人民币"},{"NPAYMENTTYPE":2,"NID":1077,"SMONEYNAMECN":"银联"},{"NPAYMENTTYPE":3,"NID":1078,"SMONEYNAMECN":"一卡通"},{"NPAYMENTTYPE":5,"NID":1079,"SMONEYNAMECN":"支付宝"},{"NPAYMENTTYPE":8,"NID":1080,"SMONEYNAMECN":"微信"},{"NPAYMENTTYPE":6,"NID":1091,"SMONEYNAMECN":"预付款"},{"NPAYMENTTYPE":null,"NID":null,"SMONEYNAMECN":null},{"NPAYMENTTYPE":13,"NID":1103,"SMONEYNAMECN":"预授权"},{"NPAYMENTTYPE":14,"NID":1116,"SMONEYNAMECN":"会员卡"},{"NPAYMENTTYPE":14,"NID":1117,"SMONEYNAMECN":"会员卡GG"},{"NPAYMENTTYPE":5,"NID":1120,"SMONEYNAMECN":"支付宝"},{"NPAYMENTTYPE":8,"NID":1121,"SMONEYNAMECN":"微信"}]
     * isSuccess : true
     * error : null
     */

    public boolean isSuccess;
    public String error;
    public List<DtBean> dt;

    /**
     * NPAYMENTTYPE : 11.0
     * NID : 1095.0
     * SMONEYNAMECN : 挂账
     */

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DtBean> getDt() {
        return dt;
    }

    public void setDt(List<DtBean> dt) {
        this.dt = dt;
    }

    public static class DtBean {
        /**
         * NPAYMENTTYPE : 1.0
         * NID : 1076.0
         * SMONEYNAMECN : 人民币
         */

        @JSONField(name = "NPAYMENTTYPE")
        public int NPAYMENTTYPEX;
        @JSONField(name = "NID")
        public int NIDX;
        @JSONField(name = "SMONEYNAMECN")
        public String SMONEYNAMECNX;

        public int getNPAYMENTTYPEX() {
            return NPAYMENTTYPEX;
        }

        public void setNPAYMENTTYPEX(int NPAYMENTTYPEX) {
            this.NPAYMENTTYPEX = NPAYMENTTYPEX;
        }

        public int getNIDX() {
            return NIDX;
        }

        public void setNIDX(int NIDX) {
            this.NIDX = NIDX;
        }

        public String getSMONEYNAMECNX() {
            return SMONEYNAMECNX;
        }

        public void setSMONEYNAMECNX(String SMONEYNAMECNX) {
            this.SMONEYNAMECNX = SMONEYNAMECNX;
        }

        private boolean isChecked;


        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

    }


}
