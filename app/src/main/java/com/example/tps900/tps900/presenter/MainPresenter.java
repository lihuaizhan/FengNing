package com.example.tps900.tps900.presenter;

import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.EditDialog;
import com.example.tps900.tps900.Utlis.Updata_Bean;
import com.example.tps900.tps900.contract.MainContract;
import com.example.tps900.tps900.model.MainModel;
import com.godfery.pay.HttpUtils;

import java.util.List;

import static com.example.tps900.tps900.activity.BaseActivity.progressDialog;

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

public class MainPresenter implements MainContract.Presenter, MainModel.MianDataCallBack {
    MainModel mainModel;
    MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        mainModel = new MainModel(this);
    }

    /**
     * app更新方法
     * 根据调用model来获取
     */
    @Override
    public void getP_UpdataAPP() {
        HttpUtils.showProgressDialog(progressDialog);
        mainModel.getM_UpdataApp();
    }

    /**
     * 获取线下售卖和线下检票,售票信息
     *
     * @param userName
     * @param password
     */
    @Override
    public void p_getGoodsInfo(String userName, String password) {
        mainModel.m_getGoodsInfo(userName, password);
    }

    /**
     * 获取线下售卖和线下检票,售票信息
     *
     * @param userName
     * @param password
     */
    @Override
    public void p_getFoodsInfo(String userName, String password) {
        mainModel.GetFoodsInfo_X(userName, password);
    }

    /**
     * 显示登录框
     */
    @Override
    public void p_showDialog_Login() {
        EditDialog editDialog = new EditDialog(Constant.context);
        editDialog.show();
        editDialog.setOnPosNegClickListener(new EditDialog.OnPosNegClickListener() {
            @Override
            public void posClickListener(String name, String password) {
                p_getGoodsInfo(name, password);
            }

            @Override
            public void negCliclListener(String name, String password) {
            }
        });
    }

    /**
     * 显示登录框
     */
    @Override
    public void p_showDialog_Login_Food() {
        EditDialog editDialog = new EditDialog(Constant.context);
        editDialog.show();
        editDialog.setOnPosNegClickListener(new EditDialog.OnPosNegClickListener() {
            @Override
            public void posClickListener(String name, String password) {
                p_getFoodsInfo(name, password);
            }

            @Override
            public void negCliclListener(String name, String password) {
            }
        });
    }

    /**
     * 线下核销
     */
    @Override
    public void p_getLineWriteOff() {
        mainModel.m_getLineWriteOff();
    }

    /**
     * 线下门票售卖
     */
    @Override
    public void p_getLineTicket() {
        mainModel.m_getLineTicket();
    }

    /**
     * 线下门票方案
     */
    @Override
    public String p_Formulas() {
        return mainModel.m_Formulas();
    }

    /**
     * 获取线下可售门票信息
     */
    @Override
    public String p_getTerminalTicket() {
        return mainModel.m_getTerminalTicket();
    }

    /**
     * 获取门票的支付方式
     */
    @Override
    public String p_TerminalPayMode() {
        return mainModel.m_TerminalPayMode();
    }

    /**
     * 售票终端信息
     */
    @Override
    public String p_getTerminalInfo() {
        return mainModel.m_getTerminalInfo();
    }

    /**
     * 获取检票终端信息
     */
    @Override
    public String p_getticketDevice() {
        return mainModel.m_getticketDevice();
    }

    /**
     * 线下登录信息
     *
     * @param userName
     * @param password
     */
    @Override
    public String p_GetLogin(String userName, String password) {
        return mainModel.m_GetLogin(userName, password);
    }

    /**
     * 获取营业点信息
     */
    @Override
    public String p_GetZoneInfo() {
        return mainModel.m_GetZoneInfo();
    }

    /**
     * 获取线下商品分类和商品详情
     */
    @Override
    public String p_GetgoodsCategories() {
        return mainModel.m_GetgoodsCategories();
    }

    /**
     * 接口调用成功调用此方法
     *
     * @param msg
     */
    @Override
    public void onSuccess(String msg) {
        view.v_getGoodsInfoOK(msg);
    }

    /**
     * 接口成功回调数据
     *
     * @param updataBeanList 返回数据集合
     */
    @Override
    public void onApkSuccess(List<Updata_Bean.SuccessBean> updataBeanList) {
        HttpUtils.exitProgressDialog(progressDialog);
        view.getV_UpdataApp_Ok(updataBeanList);
    }

    /**
     * 接口返回失败的回调
     *
     * @param Err
     */
    @Override
    public void onError(String Err) {
        HttpUtils.exitProgressDialog(progressDialog);
        view.getV_UpdataApp_Err(Err);
    }
}
