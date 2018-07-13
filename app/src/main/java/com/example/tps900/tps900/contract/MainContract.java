package com.example.tps900.tps900.contract;

import com.example.tps900.tps900.Utlis.Updata_Bean;

import java.util.List;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/11/27 14:33
 * 修改人：zxh
 * 修改时间：2017/11/27 14:33
 * 修改备注：
 */

public interface MainContract {
    interface Model {
        /**
         * app更新方法
         */
        void getM_UpdataApp();

        /**
         * 获取线下售卖和线下检票,售票信息
         *
         * @param userName
         * @param password
         */
        void m_getGoodsInfo(String userName, String password);

        /**
         * 获取线下检票和售票信息
         */
        void m_getGetTerminalInfo();

        /**
         * 线下核销
         */
        void m_getLineWriteOff();

        /**
         * 线下门票售卖
         */
        void m_getLineTicket();

        /**
         * 线下门票方案
         */
        String m_Formulas();

        /**
         * 获取线下可售门票信息
         */
        String m_getTerminalTicket();

        /**
         * 获取门票的支付方式
         */
        String m_TerminalPayMode();

        /**
         * 售票终端信息
         */
        String m_getTerminalInfo();

        /**
         * 获取检票终端信息
         */

        String m_getticketDevice();

        /**
         * 线下登录信息
         */
        String m_GetLogin(String userName, String password);

        /**
         * 获取营业点信息
         */
        String m_GetZoneInfo();

        /**
         * 获取线下商品分类和商品详情
         */
        String m_GetgoodsCategories();

    }

    interface View {
        /**
         * 获取APP更新方法成功回调
         * 通过调用Presenter来获取信息
         *
         * @param updataBeanList
         */
        void getV_UpdataApp_Ok(List<Updata_Bean.SuccessBean> updataBeanList);

        /**
         * 获取app更新方法失败回调
         * 通过调用Presenter来获取信息
         *
         * @param err
         */
        void getV_UpdataApp_Err(String err);

        /**
         *商品售卖
         */
        void v_getGoodsInfoOK(String msg);

    }

    interface Presenter {
        /**
         * app更新方法
         * 根据调用model来获取
         */
        void getP_UpdataAPP();

        /**
         * 获取线下售卖和线下检票,售票信息
         *
         * @param userName
         * @param password
         */
        void p_getGoodsInfo(String userName, String password);

        /**
         * 获取线下售卖和线下检票,售票信息
         *
         * @param userName
         * @param password
         */
        void p_getFoodsInfo(String userName, String password);

        /**
         * 显示登录框
         */
        void p_showDialog_Login();

        /**
         * 显示登录框
         */
        void p_showDialog_Login_Food();

        /**
         * 线下核销
         */
        void p_getLineWriteOff();

        /**
         * 线下门票售卖
         */
        void p_getLineTicket();

        /**
         * 线下门票方案
         */
        String p_Formulas();

        /**
         * 获取线下可售门票信息
         */
        String p_getTerminalTicket();

        /**
         * 获取门票的支付方式
         */
        String p_TerminalPayMode();

        /**
         * 售票终端信息
         */
        String p_getTerminalInfo();

        /**
         * 获取检票终端信息
         */

        String p_getticketDevice();

        /**
         * 线下登录信息
         */
        String p_GetLogin(String userName, String password);

        /**
         * 获取营业点信息
         */
        String p_GetZoneInfo();

        /**
         * 获取线下商品分类和商品详情
         */
        String p_GetgoodsCategories();
    }
}
