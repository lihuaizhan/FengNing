package com.example.tps900.tps900.Bean;

import java.util.List;


/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/27 11:30
 * 修改人：zxh
 * 修改时间：2017/4/27 11:30
 * 修改备注：
 */

public class SalePayCodeBean {

    /**
     * payModes : [{"nid":1076,"smoneynamecn":"人民币"},{"nid":1079,"smoneynamecn":"支付宝"},{"nid":1080,"smoneynamecn":"微信"},{"nid":1078,"smoneynamecn":"一卡通"}]
     * flag : true
     * erro :
     */

    public boolean flag;
    public String erro;
    public List<PayModesBean> payModes;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public List<PayModesBean> getPayModes() {
        return payModes;
    }

    public void setPayModes(List<PayModesBean> payModes) {
        this.payModes = payModes;
    }

    public static class PayModesBean {
        /**
         * nid : 1076
         * smoneynamecn : 人民币
         */
        public int nid;
        public int npaymenttype;
        public String smoneynamecn;

        public void setNpaymenttype(int npaymenttype) {
            this.npaymenttype = npaymenttype;
        }

        public int getNpaymenttype() {
            return npaymenttype;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getSmoneynamecn() {
            return smoneynamecn;
        }

        public void setSmoneynamecn(String smoneynamecn) {
            this.smoneynamecn = smoneynamecn;
        }
    }
}
