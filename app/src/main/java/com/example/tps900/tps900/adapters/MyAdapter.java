package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.Bean.GoodsInStockBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.activity.RetailActivity;
import com.example.tps900.tps900.sql.PdaDaoUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;


/**
 * Created by wo on 2017/5/2.
 */

public class MyAdapter extends BaseAdapter {

    public static final String TAG = "MyAdapter";

    private LayoutInflater mInflater;
    private ArrayList<DetailBean.DtBean> beans;
    private TextView settlement;
    private TextView shoppingPrise;
    private TextView shoppingNum;
    private Context context;
    private TextView defaultText;

    public MyAdapter(Context context, ArrayList<DetailBean.DtBean> beans, TextView settlement, TextView shoppingPrise, TextView shoppingNum, TextView defaultText) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.beans = beans;
        this.settlement = settlement;
        this.shoppingPrise = shoppingPrise;
        this.shoppingNum = shoppingNum;
        this.defaultText = defaultText;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public DetailBean.DtBean getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DetailBean.DtBean detailBean01 = beans.get(i);
        LogUtil.i(TAG, "detailBean01--->" + detailBean01.getGoodsCount());

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_cart, null);
            holder.name = (TextView) view.findViewById(R.id.name_ticket_cart);
            holder.relativeLayout = (RelativeLayout) view.findViewById(R.id.item_cart_rv);
            holder.price = (TextView) view.findViewById(R.id.price_ticket_cart);
            holder.minus = (Button) view.findViewById(R.id.btn_minus_cart);
            holder.count = (TextView) view.findViewById(R.id.text_Count_cart);
            holder.plus = (Button) view.findViewById(R.id.btn_plus_cart);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final DetailBean.DtBean detailBean = beans.get(i);
        if (!detailBean.IsSale) {
            holder.relativeLayout.setBackgroundColor(Color.GRAY);
        } else {
            holder.relativeLayout.setBackgroundColor(Color.WHITE);
        }
        holder.name.setText(detailBean.getVCOMMYNAME());
        LogUtil.e("名字---------->", detailBean.getVCOMMYNAME() + "---------");
        LogUtil.e("价格---------->", String.valueOf(detailBean.getPRICE()) + "--------");
        LogUtil.e("数量---------->", String.valueOf(detailBean.getGoodsCount()) + "-------");

        holder.price.setText(String.valueOf(detailBean.getPRICE()));
        holder.count.setText(String.valueOf(detailBean.getGoodsCount()));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean query_gzoneComm_stock = PdaDaoUtils.query_GzoneComm_Stock(
                        "select * from GzoneComm where VCOMMYNAME='" + detailBean.getVCOMMYNAME() + "'And PRICE='" + detailBean.getPRICE() + "'");
                if (query_gzoneComm_stock) {
                    getGoodsInStock(detailBean.getNCOMMID(), detailBean, holder);
                } else {
                    int number = detailBean.getGoodsCount();
                    number = number + 1;
                    LogUtil.i(TAG, "数量----->" + number);
                    detailBean.setGoodsCount(number);
                    holder.count.setText(String.valueOf(detailBean.getGoodsCount()));

                    String trim = shoppingNum.getText().toString().trim();
                    int totalNo = parseInt(trim);
                    totalNo = totalNo + 1;
                    shoppingNum.setText(String.valueOf(totalNo));

                    String trim1 = shoppingPrise.getText().toString().trim();
                    double totalMoney = Double.parseDouble(trim1);
                    totalMoney = totalMoney + detailBean.getPRICE();
                    shoppingPrise.setText(new DecimalFormat("0.00").format(totalMoney));

                }


            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = detailBean.getGoodsCount();
                if (number > 0) {
                    number = number - 1;
                    detailBean.setGoodsCount(number);
                    holder.count.setText(String.valueOf(detailBean.getGoodsCount()));

                    if (number == 0) {
                        beans.remove(detailBean);
                        notifyDataSetChanged();

                    } else {
                        detailBean.setGoodsCount(number);
                        holder.count.setText(String.valueOf(detailBean.getGoodsCount()));
                    }

                }

                if (Constant.BEANS.size() == 0) {
                    settlement.setVisibility(View.GONE);
                    shoppingPrise.setTextColor(context.getResources().getColor(R.color.default_shopping));
                    shoppingNum.setVisibility(View.GONE);
                    defaultText.setVisibility(View.VISIBLE);
                    shoppingPrise.setText("0.00");
                }

                String trim = shoppingNum.getText().toString().trim();
                int totalNo = parseInt(trim);
                if (totalNo > 0) {
                    totalNo = totalNo - 1;
                    shoppingNum.setText(String.valueOf(totalNo));
                }


                String trim1 = shoppingPrise.getText().toString().trim();
                double totalMoney = Double.parseDouble(trim1);
                if (totalMoney > 0) {
                    totalMoney = totalMoney - detailBean.getPRICE();
                    shoppingPrise.setText(new DecimalFormat("0.00").format(totalMoney));

                }


            }
        });

        return view;
    }

    /**
     * 获取商品库存数量
     */
    public void getGoodsInStock(String goodId, DetailBean.DtBean detailBean, ViewHolder holder) {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                String goodsstock = GsWebServiceUtils.getGOODSSTOCK(String.valueOf(Constant.ZONEID), goodId);
                if (!goodsstock.equals("err")) {
                    GoodsInStockBean goodsInStockBean = JSON.parseObject(goodsstock, GoodsInStockBean.class);
                    if (goodsInStockBean.isSuccess) {
                        if (goodsInStockBean.isStock) {
                            Constant.GoodsInStock = goodsInStockBean.StorckNum;
                            int goodsList = RetailActivity.isGoodsList(detailBean);
                            if (Constant.GoodsInStock < 3.0||Constant.GoodsInStock==3) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.show(context, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                    }
                                });
                                if (Constant.GoodsInStock > 0 && goodsList < Constant.GoodsInStock) {
                                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showCenter(context, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                        }
                                    });
                                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                          
                                            int number = detailBean.getGoodsCount();
                                            number = number + 1;
                                            LogUtil.i(TAG, "数量----->" + number);
                                            detailBean.setGoodsCount(number);
                                            holder.count.setText(String.valueOf(detailBean.getGoodsCount()));

                                            String trim = shoppingNum.getText().toString().trim();
                                            int totalNo = parseInt(trim);
                                            totalNo = totalNo + 1;
                                            shoppingNum.setText(String.valueOf(totalNo));

                                            String trim1 = shoppingPrise.getText().toString().trim();
                                            double totalMoney = Double.parseDouble(trim1);
                                            totalMoney = totalMoney + detailBean.getPRICE();
                                            shoppingPrise.setText(new DecimalFormat("0.00").format(totalMoney));
                                        }
                                    });
                                } else if (Constant.GoodsInStock <= 0) {
                                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showCenter(context, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                        }
                                    });
                                }
                            } else if (goodsList < Constant.GoodsInStock && Constant.GoodsInStock > 3.0) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int number = detailBean.getGoodsCount();
                                        number = number + 1;
                                        LogUtil.i(TAG, "数量----->" + number);
                                        detailBean.setGoodsCount(number);
                                        holder.count.setText(String.valueOf(detailBean.getGoodsCount()));

                                        String trim = shoppingNum.getText().toString().trim();
                                        int totalNo = parseInt(trim);
                                        totalNo = totalNo + 1;
                                        shoppingNum.setText(String.valueOf(totalNo));

                                        String trim1 = shoppingPrise.getText().toString().trim();
                                        double totalMoney = Double.parseDouble(trim1);
                                        totalMoney = totalMoney + detailBean.getPRICE();
                                        shoppingPrise.setText(new DecimalFormat("0.00").format(totalMoney));
                                    }
                                });
                            } else if (goodsList >= Constant.GoodsInStock) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showCenter(context, "已选商品库存不足");
                                    }
                                });
                            }
                        }
                    } else {
                        ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                            @Override
                            public void run() {
                                Dailog.ErrDialog2(context, "获取商品库存数量失败"  , "");
                            }
                        });
                    }
                } else {
                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            Dailog.ErrDialog2(context, "获取商品库存数量失败\n请检查网络或配置", "");
                        }
                    });
                }
            }
        });

    }

    static class ViewHolder {

        public TextView name;
        public TextView price;
        public Button minus;
        public TextView count;
        public Button plus;
        public RelativeLayout relativeLayout;


    }

}
