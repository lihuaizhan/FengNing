package com.example.tps900.tps900.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.Updata_Bean;
import com.example.tps900.tps900.contract.LoginContract;
import com.example.tps900.tps900.presenter.LoginPresenter;
import com.example.tps900.tps900.sql.PdaDaoUtils;
import com.example.tps900.tps900.updata.UpdateEditionActivity;
import com.godfery.Sqlite.DBUtils;
import com.godfery.keyboard.KeyboardUtil;
import com.godfery.pay.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.tps900.tps900.Utlis.Constant.DOWN_APK_URL;
import static com.example.tps900.tps900.Utlis.OtherUtils.getTime;
//import static com.example.tps900.tps900.activity.PayActivitys.MemberPaySubmit;
//import static com.example.tps900.tps900.activity.PayActivitys.MemberPaySubmit_2;
import static com.godfery.keyboard.CustomEditLintener.EditListener;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    Button btn_Login;
    Button btn_Set;
    EditText userName;
    ImageView imgIcon;

    EditText password;

    ImageButton imgEye;

    ProgressBar mainProgressBar;

    RelativeLayout relativeLayout;

    TextView data_tv;
    //登录的presenter
    private LoginContract.Presenter presenter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        userName = (EditText) findViewById(R.id.edit_userName);
        imgIcon = (ImageView) findViewById(R.id.img_icon);
        password = (EditText) findViewById(R.id.edit_Password);
        imgEye = (ImageButton) findViewById(R.id.img_eye);
        btn_Login = (Button) findViewById(R.id.btn_login);
        btn_Set = (Button) findViewById(R.id.btn_set);
        mainProgressBar = (ProgressBar) findViewById(R.id.main_progressBar);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main2);
        data_tv = (TextView) findViewById(R.id.button_cost_tv);
        presenter = new LoginPresenter(this);
        Constant.SetOrMain = "Set";
        Constant.dbUtils = new DBUtils(LoginActivity.this, "galasys.db");
        presenter.getP_UpdataAPP();
        presenter.getP_readSettingXml();
        PdaDaoUtils.OpenSql_CreatTable(LoginActivity.this);
        OtherUtils.setConstants(LoginActivity.this);
    }

    @Override
    public void initData() {
        EditListener(password);
        EditListener(userName);
        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                userName.setHint("");
                password.setHint("请输入密码");
                new KeyboardUtil(getApplicationContext(), LoginActivity.this, userName, R.id.schemas_key_keyboard_view).showKeyboard();
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password.setHint("");
                userName.setHint("请输入用户名");
                new KeyboardUtil(getApplicationContext(), LoginActivity.this, password, R.id.schemas_key_keyboard_view).showKeyboard();
            }
        });
    }

    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn_login) {
           presenter.getP_logininfoandticketinfo(userName.getText().toString().trim(), password.getText().toString().trim());
//            MemberPaySubmit();
//            MemberPaySubmit_2();
        } else if (id == R.id.btn_set) {
            openActivityAndCloseThis(SettingActivity.class);
        } else if (id == R.id.img_eye) {
            ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                @Override
                public void run() {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //创建一个订时器
                    Timer timer = new Timer();
                    //创建一个任务
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                    };
                    //延时3秒后执行任务
                    timer.schedule(timerTask, 3000);
                }
            });
        }

    }

    @Override
    public void getV_UpdataApp_Ok(List<Updata_Bean.SuccessBean> updataBeanList) {
        try {
            //包的信息
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            String packageName = packageInfo.versionName;
            for (int i = 0; i < updataBeanList.size(); i++) {
                Constant.DOWN_APK_URL = updataBeanList.get(i).getDownLoadURL();
                Constant.APK_NAME = updataBeanList.get(i).getVersionName();
                String android_v = updataBeanList.get(i).getNames();
                getServiceTime(updataBeanList.get(i).getEndData(), getTime());
                if (!TextUtils.isEmpty(android_v) && !packageName.equals(android_v)) {
                    if (!TextUtils.isEmpty(DOWN_APK_URL)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", Constant.APK_NAME);
                        bundle.putString("url", OtherUtils.transition(DOWN_APK_URL));
                        openActivity(UpdateEditionActivity.class, bundle);
                    }
                }
            }
        } catch (Exception e) {

        }
    }


    //获取服务器时间 然后进行算出剩余天数
    public void getServiceTime(String EndData, String data) {
        try {
            SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");//格式化时间
            long result = (d.parse(EndData).getTime() - d.parse(data).getTime()) / 1000 / 60 / 60 / 24;
            if (result <= 4 && result >= 0) {
                relativeLayout.setAlpha((float) 0.3);
                data_tv.setText("注册有效期剩余" + result + "天");
                relativeLayout.setVisibility(View.VISIBLE);
            } else if (result < 0) {
                relativeLayout.setAlpha((float) 0.3);
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setOnClickListener(null);  //只需如此设置，即可达到效果
                btn_Login.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void getV_UpdataApp_Err(String err) {
        relativeLayout.setAlpha((float) 0.3);
        relativeLayout.setVisibility(View.VISIBLE);
        data_tv.setText("查找服务失败，请检查服务链接");
        btn_Login.setEnabled(false);
    }

    @Override
    public void getV_LoginOK() {
        HttpUtils.exitProgressDialog(progressDialog);
        openActivityAndCloseThis(MainActivity.class);
    }
}
