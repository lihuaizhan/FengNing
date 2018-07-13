package com.example.tps900.tps900.Bean;

import java.util.List;


/**
 * 项目名称：TPS900
 * 类名称：售票方案
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/27 11:23
 * 修改人：zxh
 * 修改时间：2017/4/27 11:23
 * 修改备注：
 */

public class FormulasBean {


    /**
     * formulas : [{"nformulaid":1456,"sformulaname":"欢乐时光-TVM售票"}]
     * flag : true
     * erro :
     */

    public boolean flag;
    public String erro;
    public List<FormulaBean> formulas;

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

    public List<FormulaBean> getFormulas() {
        return formulas;
    }

    public void setFormulas(List<FormulaBean> formulas) {
        this.formulas = formulas;
    }

    public static class FormulaBean {
        /**
         * nformulaid : 1456
         * sformulaname : 欢乐时光-TVM售票
         */
        public int nformulaid;
        public String sformulaname;


        public int getNformulaid() {
            return nformulaid;
        }

        public void setNformulaid(int nformulaid) {
            this.nformulaid = nformulaid;
        }

        public String getSformulaname() {
            return sformulaname;
        }

        public void setSformulaname(String sformulaname) {
            this.sformulaname = sformulaname;
        }
    }
}
