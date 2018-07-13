package com.example.tps900.tps900.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tps900.tps900.activity.RetailActivity_XS;
import com.example.tps900.tps900.Bean.GetTicket_Bean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.adapters.Pro_type_adapter_XS;
import com.example.tps900.tps900.Bean.DetailBean;
import com.godfery.Utils.ToastUtils;

import java.util.ArrayList;


public class Fragment_pro_type_XS extends Fragment {
	private ImageView hint_img;
	private ListView mGridView;
	private Pro_type_adapter_XS adapter;
	private ArrayList result;
	private ArrayList<DetailBean> BEANS_XS;
	private RetailActivity_XS activity;
	private TextView toptype;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		BEANS_XS = new ArrayList<>();

		View view = inflater.inflate(R.layout.fragment_pro_type_xs, null);
		toptype = (TextView) view.findViewById(R.id.toptype);
		hint_img=(ImageView) view.findViewById(R.id.hint_img);
		mGridView = (ListView) view.findViewById(R.id.listView);
		result = (ArrayList) getArguments().getSerializable("result");
		String topType = getArguments().getString("topType");
		toptype.setText(topType);

		if(null != result){
			if(!result.isEmpty()){
				adapter=new Pro_type_adapter_XS(getActivity(), result);
				mGridView.setAdapter(adapter);
			}

		}

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				GetTicket_Bean.DataBean   detailBean = (	GetTicket_Bean.DataBean ) result.get(arg2);
				LogUtil.i("TAG",detailBean.getProductName());

				if(!Constant.BEANS_XS.isEmpty()){

					boolean flag = false;
					for(int i = 0;i<Constant.BEANS_XS.size();i++){
						if(detailBean.equals(Constant.BEANS_XS.get(i))){
							flag = true;
							break;
						}else{
							flag = false;
						}
					}

					if(!flag){
						//集合里没有该对象
						Constant.BEANS_XS.add(detailBean);
						detailBean.setTicketnumber(1);
						activity.updateViews(Constant.BEANS_XS);
					}else{
						//集合里由该对象
						ToastUtils.show(getActivity(),"购物车已添加该商品!");
					}
				}else{
					//集合为空
					Constant.BEANS_XS.add(detailBean);
					detailBean.setTicketnumber(1);
					activity.updateViews(Constant.BEANS_XS);

				}

			}
		});

		return view;
	}




	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		activity = (RetailActivity_XS) context;


	}

	//	private void GetTypeList() {





//		list=new ArrayList<Type>();
//		for(int i=1;i<35;i++){
//			type=new Type(i, typename+i, "");
//			list.add(type);
//		}
//		progressBar.setVisibility(View.GONE);
//	}
	
	
	/*private class LoadTask extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			String name[]=new String[]{"shopid","type"};
			String value[]=new String[]{"0","store"};
			return NetworkHandle.requestBypost("app=u_favorite&act=index",name,value);
		}
		
		@Override
		protected void onPostExecute(String result) {	
			progressBar.setVisibility(View.GONE);
			list=new ArrayList<Shop>();
			try {
				if(Constant.isDebug)System.out.println("result:"+result);
				JSONObject ob=new JSONObject(result);
				if(ob.getString("state").equals("1")){
					arrayToList(ob.getJSONArray("list"));
					adapter=new Love_shop_adapter(getActivity(), list,listView);
					listView.setAdapter(adapter);
					listView.onRefreshComplete();
					if(list.size()<20)
						listView.onPullUpRefreshFail();
					if(list.size()==0)hint_img.setVisibility(View.VISIBLE);
					else hint_img.setVisibility(View.GONE);
				}else{
					//if(tradestate.equals("0"))
						//ResultUtils.handle((Activity_order)getActivity(), ob);
				}
			} catch (Exception e) {
			//	if(tradestate.equals("0"))
					//ResultUtils.handle((Activity_order)getActivity(), "");
				e.printStackTrace();
			}	
		}
	}
	
	private void arrayToList(JSONArray array) throws JSONException{
		JSONObject ob;
		for (int i = 0; i < array.length(); i++) {
			ob=array.getJSONObject(i);
			shop=new Shop(ob.getString("shopid"),ob.getString("shopname"), ob.getString("shoplogo"), ob.getString("weixin"), ob.getString("shopurl"));
			list.add(shop);	
		   }
		}
	*/
	
	/*private class LoadTaskMore extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			String name[]=new String[]{"shopid","type"};
			String value[]=new String[]{list.get(list.size()-1).getShopid(),"store"};
			return NetworkHandle.requestBypost("app=u_favorite&act=index",name,value);
		}
		@Override
		protected void onPostExecute(String result) {
			if(Constant.isDebug)System.out.println("result:"+result);
			try {
				JSONObject ob=new JSONObject(result);
				if(ob.getString("state").equals("1")){
					JSONArray array=ob.getJSONArray("list");
					arrayToList(array);
					if(array.length()>0)
						adapter.notifyDataSetChanged();
					if(array.length()<20)
						listView.onPullUpRefreshFail();
					else 
						listView.onPullUpRefreshComplete();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}*/



}
