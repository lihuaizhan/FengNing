package com.example.tps900.tps900.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-03-16 10:48
 * 修改人：zxh
 * 修改时间：2018-03-16 10:48
 * 修改备注：
 */

public class SETTABLEINFOBean {

    /**
     * isSuccess : true
     * error : null
     * goodls : [{"IsSale":true,"IsStock":false,"GoodsId":4,"SaleNums":1,"StockNums":0,"GoodsName":"菠萝"}]
     */

    public boolean isSuccess;
    public String error;
    public List<GoodlsBean> goodls;

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

    public List<GoodlsBean> getGoodls() {
        return goodls;
    }

    public void setGoodls(List<GoodlsBean> goodls) {
        this.goodls = goodls;
    }

    public static class GoodlsBean implements Serializable {
        /**
         * IsSale : true
         * IsStock : false
         * GoodsId : 4
         * SaleNums : 1.0
         * StockNums : 0.0
         * GoodsName : 菠萝
         */
        //是否可售卖
        public boolean IsSale;
        //是否管理库存
        public boolean IsStock;
        //商品id
        public String GoodsId;
        //售卖数量
        public double SaleNums;
        //库存数量
        public double StockNums;
        //商品名称
        public String GoodsName;

        public boolean isSale() {
            return IsSale;
        }

        public void setSale(boolean sale) {
            IsSale = sale;
        }

        public boolean isStock() {
            return IsStock;
        }

        public void setStock(boolean stock) {
            IsStock = stock;
        }

        public String getGoodsId() {
            return GoodsId;
        }

        public void setGoodsId(String goodsId) {
            GoodsId = goodsId;
        }

        public double getSaleNums() {
            return SaleNums;
        }

        public void setSaleNums(double saleNums) {
            SaleNums = saleNums;
        }

        public double getStockNums() {
            return StockNums;
        }

        public void setStockNums(double stockNums) {
            StockNums = stockNums;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String goodsName) {
            GoodsName = goodsName;
        }
    }
}
