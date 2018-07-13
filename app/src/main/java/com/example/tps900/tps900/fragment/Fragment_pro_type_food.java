package com.example.tps900.tps900.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.activity.FoodActivity;
import com.example.tps900.tps900.adapters.Pro_type_adapter;
import com.example.tps900.tps900.adapters.TabletypeInfoAdapter_1;
import com.example.tps900.tps900.view.GridSpacingItemDecoration;
import com.godfery.Utils.ToastUtils;

import java.util.ArrayList;


public class Fragment_pro_type_food extends Fragment {
	private ImageView hint_img;
	private Pro_type_adapter adapter;
	private ArrayList result;
	private ArrayList<DetailBean> beans;
	private FoodActivity activity;
	private TextView toptype;
	private RecyclerView recyclerView;
	private TabletypeInfoAdapter_1 tableInfoAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		beans = new ArrayList<>();

		View view = inflater.inflate(R.layout.fragment_pro_type_food, null);
		toptype = (TextView) view.findViewById(R.id.toptype);
		hint_img=(ImageView) view.findViewById(R.id.hint_img);
		recyclerView = (RecyclerView) view.findViewById(R.id.fm_food_receyclerView);
		//显示布局风格
		recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		//设置间距
		int spanCount = 2;
		int spacing = 35;
		boolean includeEdge = true;
		recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
		result = (ArrayList) getArguments().getSerializable("result");
		String topType = getArguments().getString("topType");
		toptype.setText(topType);

		if(null != result){
			if(!result.isEmpty()){
				tableInfoAdapter = new TabletypeInfoAdapter_1(R.layout.list_pro_type_item, result, getContext());
				tableInfoAdapter.setNewData(result);
				recyclerView.setAdapter(tableInfoAdapter);
			}

		}
		tableInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				DetailBean.DtBean detailBean = (DetailBean.DtBean) result.get(position);
				LogUtil.i("TAG",detailBean.getVCOMMYNAME());

				if(!Constant.BEANS.isEmpty()){

					boolean flag = false;
					for(int i = 0; i< Constant.BEANS.size(); i++){
						if(detailBean.equals(Constant.BEANS.get(i))){
							flag = true;
							break;
						}else{
							flag = false;
						}
					}

					if(!flag){
						//集合里没有该对象
						Constant.BEANS.add(detailBean);
						detailBean.setGoodsCount(1);
						activity.updateViews(Constant.BEANS);
					}else{
						//集合里由该对象
						ToastUtils.show(getActivity(),"购物车已添加该商品!");
					}
				}else{
					//集合为空
					Constant.BEANS.add(detailBean);
					detailBean.setGoodsCount(1);
					activity.updateViews(Constant.BEANS);

				}


			}
		});

		return view;
	}




	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		activity = (FoodActivity) context;


	}


}
