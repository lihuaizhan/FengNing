package com.example.tps900.tps900.Bean;

/**
 * 项目名称：PDA_XiLing
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/11/19 16:25
 * 修改人：zxh
 * 修改时间：2017/11/19 16:25
 * 修改备注：
 */

/**
 * 主页应用列表Bean
 * @author zxh
 */
public class ApplicationListBean {
    //应用列表名称
    private String titleName;
    //应用图标id
    private int resId;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
