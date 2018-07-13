package com.example.tps900.tps900.InterFace;

import android.content.Intent;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-03-09 14:29
 * 修改人：zxh
 * 修改时间：2018-03-09 14:29
 * 修改备注：
 */

public interface FoodInterface {

    /**
     * 支付成功回调
     */
    void onPayresultOk(int requestCode, int resultCode, Intent data);

}
