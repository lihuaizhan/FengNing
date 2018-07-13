package com.example.tps900.tps900.presenter;

import com.example.tps900.tps900.Utlis.Updata_Bean;
import com.example.tps900.tps900.contract.LoginContract;
import com.example.tps900.tps900.model.LoginModel;
import com.godfery.pay.HttpUtils;

import java.util.List;

import static com.example.tps900.tps900.activity.BaseActivity.progressDialog;

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

public class LoginPresenter implements LoginContract.Presenter, LoginModel.DataCallBack {

    private LoginContract.View view;
    private LoginContract.Model model;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        model = new LoginModel(this);
    }

    /**
     * app更新方法
     * 根据调用model来获取
     */
    @Override
    public void getP_UpdataAPP() {
        HttpUtils.showProgressDialog(progressDialog);
        model.getM_UpdataApp();
    }

    /**
     * 根据用户名密码登录
     * 根据登录的信息获取门票类别和门票详情
     *
     * @param userName
     * @param passWord
     */
    @Override
    public void getP_logininfoandticketinfo(String userName, String passWord) {
        model.getM_logininfoandticketinfo(userName, passWord);

    }


    /**
     * 读取Setting设置xml文件
     */
    @Override
    public void getP_readSettingXml() {
        model.getM_readSettingXml();
    }

    /**
     * 接口成功回调数据
     *
     * @param updataBeanList 返回数据集合
     */
    @Override
    public void onSuccess(List<Updata_Bean.SuccessBean> updataBeanList) {
        HttpUtils.exitProgressDialog(progressDialog);
        view.getV_UpdataApp_Ok(updataBeanList);
    }

    /**
     * 登录成功
     */
    @Override
    public void onloginOK() {
        view.getV_LoginOK();
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
