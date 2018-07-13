package com.example.tps900.tps900.MenberBean;

/**
 * 项目名称：PDA_FengNing
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-06-23 17:00
 * 修改人：zxh
 * 修改时间：2018-06-23 17:00
 * 修改备注：
 */

public class QueryMemberInfo {

    /**
     * cardId : 13632651195
     * password : 123456
     * isGetExtValue : false
     */
    //	卡号，或者手机号，或者会员唯一标识Guid
    public String cardId;
    //	密码
    public String password;
    //是否获取扩展字段（true/false,默认是false，不获取）
    public String isGetExtValue;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsGetExtValue() {
        return isGetExtValue;
    }

    public void setIsGetExtValue(String isGetExtValue) {
        this.isGetExtValue = isGetExtValue;
    }
}
