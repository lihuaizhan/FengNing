package com.example.tps900.tps900.Bean;

import java.util.List;


/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/27 11:13
 * 修改人：zxh
 * 修改时间：2017/4/27 11:13
 * 修改备注：
 */

public class DeviceSaleBean {

    /**
     * terminals : [{"nterminalid":3264,"sterminalname":"TVM01","sterminalip":"192.168.0.1"}]
     * flag : true
     * erro :
     */

    public boolean flag;
    public String erro;
    public List<TerminalsBean> terminals;

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

    public List<TerminalsBean> getTerminals() {
        return terminals;
    }

    public void setTerminals(List<TerminalsBean> terminals) {
        this.terminals = terminals;
    }

    public static class TerminalsBean {
        /**
         * nterminalid : 3264
         * sterminalname : TVM01
         * sterminalip : 192.168.0.1
         */

        public int nterminalid;
        public String sterminalname;
        public String sterminalip;

        public int getNterminalid() {
            return nterminalid;
        }

        public void setNterminalid(int nterminalid) {
            this.nterminalid = nterminalid;
        }

        public String getSterminalname() {
            return sterminalname;
        }

        public void setSterminalname(String sterminalname) {
            this.sterminalname = sterminalname;
        }

        public String getSterminalip() {
            return sterminalip;
        }

        public void setSterminalip(String sterminalip) {
            this.sterminalip = sterminalip;
        }
    }
}
