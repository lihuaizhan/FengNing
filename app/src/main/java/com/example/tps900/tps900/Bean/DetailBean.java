package com.example.tps900.tps900.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wo on 2017/4/27.
 */

public class DetailBean implements Serializable {

    /**
     * dt : [{"NCOMMID":4,"VCOMMBARCODE":"456345","VCOMMCODING":"43563456","VCOMMYNAME":"菠萝","PRICE":15,"NPCOMMID":-1,"PNUMBER":1,"VUNITNAME":"元/件"},{"NCOMMID":13,"VCOMMBARCODE":"2而发v","VCOMMCODING":"13二vf","VCOMMYNAME":"俄方","PRICE":3758,"NPCOMMID":-1,"PNUMBER":1,"VUNITNAME":"元/个"},{"NCOMMID":15,"VCOMMBARCODE":"阿杜","VCOMMCODING":"阿萨德","VCOMMYNAME":"撒旦","PRICE":23,"NPCOMMID":-1,"PNUMBER":1,"VUNITNAME":"元/份"},{"NCOMMID":262,"VCOMMBARCODE":"342345235","VCOMMCODING":"2352345","VCOMMYNAME":"2543254235","PRICE":0,"NPCOMMID":-1,"PNUMBER":1,"VUNITNAME":"元/个"},{"NCOMMID":4,"VCOMMBARCODE":"67556","VCOMMCODING":"9876","VCOMMYNAME":"菠萝","PRICE":12,"NPCOMMID":1003,"PNUMBER":2,"VUNITNAME":"元/瓶"}]
     * isSuccess : true
     * error : null
     */

    public boolean isSuccess;
    public String error;
    public List<DtBean> dt;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

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

    public static class DtBean implements Serializable {
        /**
         * NCOMMID : 4
         * VCOMMBARCODE : 456345
         * VCOMMCODING : 43563456
         * VCOMMYNAME : 菠萝
         * PRICE : 15.0
         * NPCOMMID : -1
         * PNUMBER : 1.0
         * VUNITNAME : 元/件
         */

        public String NCOMMID;
        public String VCOMMBARCODE;
        public String VCOMMCODING;
        public String VCOMMYNAME;
        public double PRICE;
        public int NPCOMMID;
        public int PNUMBER;
        public String VUNITNAME;

        public int GoodsCount;
        public int ClassId;
        //是否管理库存
        public int NCOMMISINVENTORY;
        //是否可售卖
        public boolean IsSale;
        //库存数量
        public double StockNums;

        public boolean isSale() {
            return IsSale;
        }

        public void setSale(boolean sale) {
            IsSale = sale;
        }

        public double getStockNums() {
            return StockNums;
        }

        public void setStockNums(double stockNums) {
            StockNums = stockNums;
        }

        public int getNCOMMISINVENTORY() {
            return NCOMMISINVENTORY;
        }

        public void setNCOMMISINVENTORY(int NCOMMISINVENTORY) {
            this.NCOMMISINVENTORY = NCOMMISINVENTORY;
        }

        public int getClassId() {
            return ClassId;
        }

        public void setClassId(int classId) {
            ClassId = classId;
        }

        public int getGoodsCount() {
            return GoodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            GoodsCount = goodsCount;
        }

        public String getNCOMMID() {
            return NCOMMID;
        }

        public void setNCOMMID(String NCOMMID) {
            this.NCOMMID = NCOMMID;
        }

        public String getVCOMMBARCODE() {
            return VCOMMBARCODE;
        }

        public void setVCOMMBARCODE(String VCOMMBARCODE) {
            this.VCOMMBARCODE = VCOMMBARCODE;
        }

        public String getVCOMMCODING() {
            return VCOMMCODING;
        }

        public void setVCOMMCODING(String VCOMMCODING) {
            this.VCOMMCODING = VCOMMCODING;
        }

        public String getVCOMMYNAME() {
            return VCOMMYNAME;
        }

        public void setVCOMMYNAME(String VCOMMYNAME) {
            this.VCOMMYNAME = VCOMMYNAME;
        }

        public double getPRICE() {
            return PRICE;
        }

        public void setPRICE(double PRICE) {
            this.PRICE = PRICE;
        }

        public int getNPCOMMID() {
            return NPCOMMID;
        }

        public void setNPCOMMID(int NPCOMMID) {
            this.NPCOMMID = NPCOMMID;
        }

        public double getPNUMBER() {
            return PNUMBER;
        }

        public void setPNUMBER(int PNUMBER) {
            this.PNUMBER = PNUMBER;
        }

        public String getVUNITNAME() {
            return VUNITNAME;
        }

        public void setVUNITNAME(String VUNITNAME) {
            this.VUNITNAME = VUNITNAME;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            DetailBean.DtBean that = (DetailBean.DtBean) o;

            if (NCOMMID != that.NCOMMID) {
                return false;
            }
            if (Double.compare(that.PRICE, PRICE) != 0) {
                return false;
            }
            if (NPCOMMID != that.NPCOMMID) {
                return false;
            }
            if (PNUMBER != that.PNUMBER) {
                return false;
            }
            if (!VCOMMCODING.equals(that.VCOMMCODING)) {
                return false;
            }
            return VCOMMYNAME.equals(that.VCOMMYNAME);

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = NCOMMID.hashCode();
            result = 31 * result + VCOMMBARCODE.hashCode();
            result = 31 * result + VCOMMCODING.hashCode();
            result = 31 * result + VCOMMYNAME.hashCode();
            temp = Double.doubleToLongBits(PRICE);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + NPCOMMID;
            result = 31 * result + PNUMBER;
            result = 31 * result + GoodsCount;
            result = 31 * result + ClassId;
            return result;
        }

        @Override
        public String toString() {
            return "DetailBean{" +
                    "NCOMMID=" + NCOMMID +
                    ", VCOMMBARCODE='" + VCOMMBARCODE + '\'' +
                    ", VCOMMCODING='" + VCOMMCODING + '\'' +
                    ", VCOMMYNAME='" + VCOMMYNAME + '\'' +
                    ", PRICE=" + PRICE +
                    ", NPCOMMID=" + NPCOMMID +
                    ", PNUMBER=" + PNUMBER +
                    ", goodsCount=" + GoodsCount +
                    ", classId=" + ClassId +
                    '}';
        }
    }
}
