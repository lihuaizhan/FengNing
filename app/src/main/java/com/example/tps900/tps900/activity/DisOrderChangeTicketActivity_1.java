package com.example.tps900.tps900.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.DisOrderBean;
import com.example.tps900.tps900.Bean.GetDisODerBean;
import com.example.tps900.tps900.Bean.GetUpadateTicketBean;
import com.example.tps900.tps900.Bean.GoodsInfo;
import com.example.tps900.tps900.Bean.StoreInfo;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.adapters.ShopcartAdapter;
import com.godfery.Utils.ToastUtils;
import com.godfery.keyboard.KeyboardUtil;
import com.godfery.pay.HttpUtils;
import com.godfery.Zxing.CaptureActivity;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.idcard.IdCard;
import com.telpo.tps550.api.idcard.IdentityInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.Dailog.ISPrintTicket;
import static com.example.tps900.tps900.Utlis.OtherUtils.getTime;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.DisOrderChangeTicket;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetDisOrder;
import static com.godfery.keyboard.CustomEditLintener.EditListener;

/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/17 13:24
 * 修改人：zxh
 * 修改时间：2017/4/17 13:24
 * 修改备注：
 */

public class DisOrderChangeTicketActivity_1 extends Activity implements ShopcartAdapter.CheckInterface,
        ShopcartAdapter.ModifyCountInterface, ShopcartAdapter.GroupEdtorListener {

    EditText ticketEtTicketCode;

    ImageView ticketImgDeleteCode;

    Button ticketQueryTicket;

    Button ticketBtnBor;

    Button ticketBtnIdcard;

    Button ticketBtnComit;

    CheckBox allChekbox;

    ExpandableListView fmPnExListView;

    TextView fmPhoneYxOderNum;

    TextView fmPhoneYxTicketNum;

    LinearLayout dataLv;
    private String TAG = "TicketVerActivity";
    private ProgressDialog progressDialog;
    private IntentBroadcastReceiver ibr;
    private DisOrderBean disOrderBean;//查询兑票Bean
    private IdentityInfo info;
    private GetDisODerBean getDisODerBean;
    private GetUpadateTicketBean getUpadateTicketBean;
    private ShopcartAdapter selva;
    private List<StoreInfo> groups = new ArrayList<StoreInfo>();// 组元素数据列表
    private Map<String, List<GoodsInfo>> children = new HashMap<String, List<GoodsInfo>>();// 子元素数据列表
    private ArrayList<GoodsInfo> ticketList;
    private int totalCount;
    private int ticketCount;
    private int ticketPrintCount;
    private String barcode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //强制隐藏系统软键盘
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        setContentView(R.layout.activity_disorderticket);

        Constant.context = DisOrderChangeTicketActivity_1.this;
        initView();
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:56
     * 方法名: initView
     * 方法说明: 初始化操作
     */
    public void initView() {
        dataLv = (LinearLayout) findViewById(R.id.dataLv);
        ticketEtTicketCode = (EditText) findViewById(R.id.ticket_et_ticketCode);
        ticketImgDeleteCode = (ImageView) findViewById(R.id.ticket_img_deleteCode);
        ticketQueryTicket = (Button) findViewById(R.id.ticket_QueryTicket);
        ticketBtnBor = (Button) findViewById(R.id.ticket_btn_bor);
        ticketBtnIdcard = (Button) findViewById(R.id.ticket_btn_idcard);
        ticketBtnComit = (Button) findViewById(R.id.ticket_btn_comit);
        allChekbox = (CheckBox) findViewById(R.id.all_chekbox);
        fmPnExListView = (ExpandableListView) findViewById(R.id.fm_pn_exListView);
        fmPhoneYxOderNum = (TextView) findViewById(R.id.fm_phone_yx_OderNum);
        fmPhoneYxTicketNum = (TextView) findViewById(R.id.fm_phone_yxTicketNum);
        progressDialog = new ProgressDialog(DisOrderChangeTicketActivity_1.this);
        EditListener(ticketEtTicketCode);
        ticketList = new ArrayList<>();
        //注册广播 添加动作
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Intent");
        intentFilter.addAction("WebService");
        if (ibr == null) {
            ibr = new IntentBroadcastReceiver();
        }
        this.registerReceiver(ibr, intentFilter);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ticket_et_ticketCode:
                ticketEtTicketCode.setHint("请输入门票串码");
                new KeyboardUtil(getApplicationContext(), DisOrderChangeTicketActivity_1.this, ticketEtTicketCode, R.id.schemas_key_keyboard_view).showKeyboard();
                break;
            case R.id.ticket_img_deleteCode://删除门票二维码
                ticketEtTicketCode.setText("");
                break;
            case R.id.ticket_QueryTicket://查询二维码
                String ticketCode = ticketEtTicketCode.getText().toString().trim();
                if (TextUtils.isEmpty(ticketCode)) {
                    ToastUtils.show(this, "请输入门票串码");
                } else {
                    barcode = ticketCode;
                    HttpUtils.showProgressDialog(progressDialog);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Webservice_GetDisOrder("", barcode, "");
                        }
                    }).start();
                }
                break;
            case R.id.ticket_btn_bor://扫描
                Intent intent = new Intent(DisOrderChangeTicketActivity_1.this, CaptureActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.ticket_btn_idcard://身份证
                DataAndClear(true, null);
                new GetIDInfoTask().execute();
                break;
            case R.id.ticket_btn_comit://确定
                ticket_list();
                if (ticketList.size() == 0) {
                    ToastUtils.show(DisOrderChangeTicketActivity_1.this, "请选择要兑换的票");
                } else {
                    ISPrintTicket(DisOrderChangeTicketActivity_1.this, "是否兑换此票");
                }
                break;
            case R.id.ticket_lv_back://返回上一页面
                DisOrderChangeTicketActivity_1.this.startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            case R.id.all_chekbox:
                doCheckAll();
                break;
            default:
                break;
        }
    }

    /**
     * @author zxh
     * created at 2017/10/11 10:07
     * 方法名: 线上兑票查询
     * 方法说明:
     */
    public void Webservice_GetDisOrder(final String Ecode, final String CardNo, final String OrderNo) {
        String getDisOrder = GetDisOrder(Ecode, CardNo, OrderNo);
        HttpUtils.exitProgressDialog(progressDialog);
        if (TextUtils.isEmpty(getDisOrder) || getDisOrder.equals("err")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ErrDialog2(DisOrderChangeTicketActivity_1.this, "请求数据失败 请检查网络或者检查配置", "");
                }
            });
        } else if (!TextUtils.isEmpty(getDisOrder) && !getDisOrder.equals("err")) {
            getDisODerBean = JSON.parseObject(getDisOrder, GetDisODerBean.class);
            final List<GetDisODerBean.ListDataBean> getListDIsOrder = new ArrayList<GetDisODerBean.ListDataBean>();
            if (getDisODerBean.Success && getDisODerBean.getListData() != null) {
                for (int i = 0; i < getDisODerBean.getListData().size(); i++) {
                    if (getDisODerBean.getListData().get(i).getIsPrint().equals("0")) {
                        GetDisODerBean.ListDataBean getDisODerBean1 = new GetDisODerBean.ListDataBean();
                        getDisODerBean1.setOrderNo(getDisODerBean.getListData().get(i).getOrderNo());
                        getDisODerBean1.setProductId(getDisODerBean.getListData().get(i).getProductId());
                        getDisODerBean1.setProductName(getDisODerBean.getListData().get(i).getProductName());
                        getDisODerBean1.setProductCount(getDisODerBean.getListData().get(i).getProductCount());
                        getDisODerBean1.setProductPrice(getDisODerBean.getListData().get(i).getProductPrice());
                        getDisODerBean1.setPlayEndDate(getDisODerBean.getListData().get(i).getPlayEndDate());
                        getDisODerBean1.setPlayStartDate(getDisODerBean.getListData().get(i).getPlayStartDate());
                        getDisODerBean1.setEcode(getDisODerBean.getListData().get(i).getEcode());
                        getListDIsOrder.add(getDisODerBean1);
                    }
                }
                if (getListDIsOrder.size() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ErrDialog2(DisOrderChangeTicketActivity_1.this, "该订单已核销 ", "");
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DataAndClear(false, getListDIsOrder);
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ErrDialog2(DisOrderChangeTicketActivity_1.this, "查询票信息失败 ", "");
                    }
                });
            }
        }
    }

    /**
     * @author zxh
     * created at 2017/10/11 10:23
     * 方法名: 取票
     * 方法说明:
     */
    public void btn_comit(String Ecode, String OrderNo, String ProductId) {
        String disjson = DisOrderChangeTicket(Ecode, OrderNo, ProductId, "0");
        if (!TextUtils.isEmpty(disjson) && !disjson.equals("err")) {
            disOrderBean = JSON.parseObject(disjson, DisOrderBean.class);
            if (disOrderBean.Success) {
                ticketPrintCount--;
                if (ticketPrintCount == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < ticketList.size(); i++) {
                                TpsN900PrintUtils.TpsN900Print_ticket(DisOrderChangeTicketActivity_1.this,"散客兑票",
                                        "\n订单号：" + ticketList.get(i).getOrderNo() +
                                                "\n门票名称：" + ticketList.get(i).getProductName() +
                                                "\n门票价格：" + ticketList.get(i).getProductPrice() + "元" +
                                                "\n入园开始日期：" + ticketList.get(i).getPlayStartDate().substring(0, 10) +
                                                "\n入园结束日期：" + ticketList.get(i).getPlayEndDate().substring(0, 10) +
                                                "\n门票兑换日期：" + getTime() +
                                                "\n可入园人数：" + ticketList.get(i).getProductCount() + "人"
                                                + "\n门票二维码："
                                        , ticketList.get(i).getEcode(), "温馨提示:此票只作为兑票凭证,不可视为报销凭证");
                            }
                            DataAndClear(true, null);
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ErrDialog2(DisOrderChangeTicketActivity_1.this, "兑票失败" + "\n" + "该票已兑换并打印", "");
                        DataAndClear(true, null);
                    }
                });
            }

        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ErrDialog2(DisOrderChangeTicketActivity_1.this, "请求数据失败 请检查网络", "");
                    DataAndClear(true, null);
                }
            });
        }
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:52
     * 方法名: 扫描核销方法
     * 方法说明: 扫描到结果进行查询核销
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (data != null) {
            barcode = data.getStringExtra("result");
            if (!TextUtils.isEmpty(barcode)) {
                HttpUtils.showProgressDialog(progressDialog);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Webservice_GetDisOrder(barcode, "", "");
                    }
                }).start();
            }
        } else {
            Toast.makeText(DisOrderChangeTicketActivity_1.this, "扫描结果为空", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * @author zxh
     *         created at 2017/6/7 15:51
     *         方法名:  IntentBroadcastReceiver
     *         方法说明: 广播接收者  利用广播跳转和访问网络
     */
    public class IntentBroadcastReceiver extends BroadcastReceiver {
        public IntentBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "Intent":
                    DisOrderChangeTicketActivity_1.this.startActivity(new Intent(DisOrderChangeTicketActivity_1.this, MainActivity.class));
                    finish();
                    break;
                case "WebService"://访问接口
                    ticketPrintCount = ticketList.size();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < ticketList.size(); i++) {
                                btn_comit(ticketList.get(i).getEcode(), ticketList.get(i).getOrderNo(), ticketList.get(i).getProductId());
                            }
                        }
                    }).start();
                    break;
            }

        }
    }

    /**
     * @author zxh
     *         created at 2017/10/11 10:24
     *         方法名: 身份证异步处理
     *         方法说明:
     */
    private class GetIDInfoTask extends
            AsyncTask<Void, Integer, TelpoException> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ticketBtnIdcard.setEnabled(false);
            dialog = new ProgressDialog(DisOrderChangeTicketActivity_1.this);
            dialog.setTitle(getString(R.string.idcard_czz));
            dialog.setMessage(getString(R.string.idcard_ljdkq));
            dialog.setCancelable(false);
            dialog.show();
            info = null;
        }

        @Override
        protected TelpoException doInBackground(Void... arg0) {
            TelpoException result = null;
            try {
                IdCard.open(DisOrderChangeTicketActivity_1.this);
                publishProgress(1);
                info = IdCard.checkIdCard(4000);
            } catch (TelpoException e) {
                e.printStackTrace();
                result = e;
            } finally {
                IdCard.close();
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == 1) {
                dialog.setMessage(getString(R.string.idcard_hqsfzxx));
            }
        }

        @Override
        protected void onPostExecute(TelpoException result) {
            super.onPostExecute(result);
            dialog.dismiss();
            ticketBtnIdcard.setEnabled(true);
            if (result == null&&!info.getNo().equals("timeout")) {
                ticketEtTicketCode.setText(info.getNo());
                HttpUtils.showProgressDialog(progressDialog);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Webservice_GetDisOrder("", info.getNo(), "");
                    }
                }).start();
            } else {
                ToastUtils.show(DisOrderChangeTicketActivity_1.this, "读取身份证超时");

            }
        }

    }

    //----------------------------------------------------取票信息处理-----------------------------------------------------------
    //初始化门票数据适配器
    private void initEvents() {
        selva = new ShopcartAdapter(groups, children, DisOrderChangeTicketActivity_1.this);
        selva.setCheckInterface(this);// 关键步骤1,设置复选框接口
        selva.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        selva.setmListener(this);
        fmPnExListView.setAdapter(selva);
        for (int i = 0; i < selva.getGroupCount(); i++) {
            // 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
            fmPnExListView.expandGroup(i);
        }
    }

    //加载数据
    private void initDatas(List<GetDisODerBean.ListDataBean> listData) {
        int CountTicket = 0;
        for (int i = 0; i < listData.size(); i++) {
            CountTicket += Integer.valueOf(listData.get(i).getProductCount());
            groups.add(new StoreInfo(i + "", listData.get(i).getOrderNo()));
            List<GoodsInfo> products = new ArrayList<GoodsInfo>();
            products.add(new GoodsInfo(
                    i + "",
                    listData.get(i).getOrderNo(),
                    listData.get(i).getProductId(),
                    listData.get(i).getProductName(),
                    listData.get(i).getProductCount(),
                    listData.get(i).getProductPrice(),
                    listData.get(i).getPlayStartDate(),
                    listData.get(i).getPlayEndDate(),
                    listData.get(i).getEcode()));


            children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
        }
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        int totalCount = 0;
        int ticketCount = 0;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getId());
            Log.e("childs", childs.size() + "---->");
            for (int j = 0; j < childs.size(); j++) {
                GoodsInfo product = childs.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    ticketCount += Integer.valueOf(childs.get(j).getProductCount());
                }
            }
        }
        fmPhoneYxOderNum.setText(totalCount + "");//已选订单
        fmPhoneYxTicketNum.setText(ticketCount + "");//已选门票
    }

    private void ticket_list() {
        ticketList = new ArrayList<>();
        Log.e("groups", groups.size() + "---->");
        for (int i = 0; i < groups.size(); i++) {
            List<GoodsInfo> childs = children.get(groups.get(i).getId());
            Log.e("groups.get(i).getId()", groups.get(i).getId() + "---->");
            Log.e("childs", childs.size() + "---->");
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    totalCount++;
                    ticketCount += Integer.valueOf(childs.get(j).getProductCount());
                    //  totalPrice += product.getPrice() * product.getCount();

                    GoodsInfo goodsInfo = new GoodsInfo();
                    goodsInfo.setProductCount(childs.get(j).getProductCount());
                    goodsInfo.setEcode(childs.get(j).getEcode());
                    goodsInfo.setProductName(childs.get(j).getProductName());
                    goodsInfo.setProductId(childs.get(j).getProductId());
                    goodsInfo.setOrderNo(childs.get(j).getOrderNo());
                    goodsInfo.setProductPrice(childs.get(j).getProductPrice());
                    goodsInfo.setPlayStartDate(childs.get(j).getPlayStartDate());
                    goodsInfo.setPlayEndDate(childs.get(j).getPlayEndDate());
                    ticketList.add(goodsInfo);
                }
            }
        }
    }

    //全选与反选
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(allChekbox.isChecked());
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setChoosed(allChekbox.isChecked());
            }
        }
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition,
                           View showCountView, boolean isChecked) {
        GoodsInfo product = (GoodsInfo) selva.getChild(groupPosition,
                childPosition);
        int currentCount = Integer.valueOf(product.getProductCount());
        currentCount++;
        product.setProductCount(currentCount + "");
        ((TextView) showCountView).setText(currentCount + "");
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition,
                           View showCountView, boolean isChecked) {

        GoodsInfo product = (GoodsInfo) selva.getChild(groupPosition,
                childPosition);
        int currentCount = Integer.valueOf(product.getProductCount());
        if (currentCount == 1)
            return;
        currentCount--;
        product.setProductCount(String.valueOf(currentCount));
        ((TextView) showCountView).setText(currentCount + "");
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            allChekbox.setChecked(true);
        else
            allChekbox.setChecked(false);
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosiTion,
                           boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            // 不全选中
            if (childs.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        //获取店铺选中商品的总金额
        if (allChildSameState) {
            group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck()) {
            allChekbox.setChecked(true);// 全选
        } else {
            allChekbox.setChecked(false);// 反选
        }
        selva.notifyDataSetChanged();
        calculate();

    }

    private boolean isAllCheck() {

        for (StoreInfo group : groups) {
            if (!group.isChoosed())
                return false;

        }

        return true;
    }

    @Override
    public void groupEdit(int groupPosition) {
        groups.get(groupPosition).setIsEdtor(true);
        selva.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        IdCard.close();
        DisOrderChangeTicketActivity_1.this.unregisterReceiver(ibr);
    }

    /**
     * @author zxh
     * created at 2017/10/11 10:35
     * 方法名: 显示数据和清除数据
     * 方法说明:
     */
    public void DataAndClear(boolean IsClear, List<GetDisODerBean.ListDataBean> getListDIsOrder) {
        if (IsClear) {
            dataLv.setVisibility(View.GONE);
            fmPhoneYxTicketNum.setText("0");
            fmPhoneYxOderNum.setText("0");
            ticketEtTicketCode.setText("");
            groups.clear();
            children.clear();
            ticketList.clear();
            totalCount = 0;
            ticketCount = 0;
            ticketPrintCount = 0;
            barcode = "";
        } else {
            fmPhoneYxTicketNum.setText("0");
            fmPhoneYxOderNum.setText("0");
            ticketEtTicketCode.setText("");
            groups.clear();
            children.clear();
            ticketList.clear();
            totalCount = 0;
            ticketCount = 0;
            ticketPrintCount = 0;
            barcode = "";
            dataLv.setVisibility(View.VISIBLE);
            initDatas(getListDIsOrder);
            initEvents();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                ) {
            DisOrderChangeTicketActivity_1.this.startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
