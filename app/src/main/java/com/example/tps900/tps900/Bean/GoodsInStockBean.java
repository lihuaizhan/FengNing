package com.example.tps900.tps900.Bean;

/**
 * 项目名称：PDA_FuHua
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-04-04 17:26
 * 修改人：zxh
 * 修改时间：2018-04-04 17:26
 * 修改备注：
 */

public class GoodsInStockBean {

    /**
     * isSuccess : false
     * isStock : true
     * error : 无该商品信息
     * StorckNum : 0.0
     */
    //调用是否成功
    public boolean isSuccess;
    //是否有库存管理
    public boolean isStock;
    //返回的error信息
    public String error;
    //库存数量
    public double StorckNum;
}
