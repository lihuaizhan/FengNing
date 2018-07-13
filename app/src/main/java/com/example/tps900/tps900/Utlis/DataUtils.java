package com.example.tps900.tps900.Utlis;

import android.content.Context;

import com.example.tps900.tps900.sql.PdaDaoUtils;

import java.util.HashMap;


/**
 * Created by wo on 2017/4/25.
 */

public class DataUtils {

    public static final String TAG = "DataUtils";

    public static void setDatas(Context context){

        PdaDaoUtils utils = new PdaDaoUtils();
        HashMap<String, String> hashMap = utils.query_info();
        if(null != hashMap){
            if(!hashMap.isEmpty()){
                //有内容
                Constant.ADDRESS = hashMap.get("address");
                Constant.TERMINALNAME = hashMap.get("terminalName");


            }else{
                LogUtil.i(TAG,"Info表内容为空");
                return;
            }
        }else{
            LogUtil.i(TAG,"未查询到Info表内容");
            return;
        }


    }

}
