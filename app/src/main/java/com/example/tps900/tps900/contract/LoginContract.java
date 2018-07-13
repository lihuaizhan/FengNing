package com.example.tps900.tps900.contract;

import com.example.tps900.tps900.Utlis.Updata_Bean;

import java.util.List;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/11/24 9:24
 * 修改人：zxh
 * 修改时间：2017/11/24 9:24
 * 修改备注：
 */

public interface LoginContract {
    interface Model {
        /**
         * app更新方法
         */
        void getM_UpdataApp();

        /**
         * 根据用户名密码登录
         * 根据登录的信息获取门票类别和门票详情
         *
         * @param userName
         * @param passWord
         */
        void getM_logininfoandticketinfo(String userName, String passWord);


        /**
         * 读取Setting设置xml文件
         */
        void getM_readSettingXml();

        /**
         * 获取线下售票信息
         */
        void getLineTicket();

        /**
         * 获取线下核销
         */
        void getLineWriteOff();

        /**
         * 获取线下核销和售票
         */
        void getGetTerminalInfo();


        /**
         * 线下商品和餐饮售卖
         */
        void GetGoodsInfo_X(String userName, final String password);

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
         * 登录成功
         */
        void getV_LoginOK();


    }

    interface Presenter {
        /**
         * app更新方法
         * 根据调用model来获取
         */
        void getP_UpdataAPP();

        /**
         * 根据用户名密码登录
         * 根据登录的信息获取门票类别和门票详情
         *
         * @param userName
         * @param passWord
         */
        void getP_logininfoandticketinfo(String userName, String passWord);


        /**
         * 读取Setting设置xml文件
         */
        void getP_readSettingXml();
    }
}
