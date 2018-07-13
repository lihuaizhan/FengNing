package com.example.tps900.tps900.MenberBean;

import java.util.List;

/**
 * 项目名称：PDA_FengNing
 * 类名称：会员消费Data  bean对象
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-06-23 16:48
 * 修改人：zxh
 * 修改时间：2018-06-23 16:48
 * 修改备注：
 *
 会员接口地址：http://openapi.1card1.cn/
 */

public class MemberConsumption {

    /**
     * cardId : 1card1Test
     * password : 123456
     * userAccount : 10003
     * totalMoney : 40
     * totalPaid : 40
     * paidMoney : 6
     * paidPoint : 7
     * paidValue : 8
     * paidCard : 5
     * paidOther : 0
     * otherPayType : 美团券
     * paidCoupon : 10
     * thirdPayInfo : {"thirdPayType":"1","thirdPayValue":"4","authCode":"1346913505024158XX"}
     * meno : 熟人小李推荐，打折
     * consumeList : [{"barcode":"0001","name":"123","number":1,"disCount":0.8,"paidMoney":22,"meno":""},{"barcode":"0002","name":"456","number":2,"disCount":1,"paidMoney":18,"meno":""}]
     * couponList : [{"sendNoteGuid":"AE3166F5-DEEF-4FCD-B984-0683329BAB63","count":1},{"sendNoteGuid":"BE3166F5-DEEF-4FCD-B984-0683329BAB63","count":2}]
     * thirdOpenId :
     * deviceType : 2
     * uniqueCode : 1516612635
     */
    public String cardId;
    public String password;
    public String userAccount;
    public double totalMoney;
    public double totalPaid;
    public double paidMoney;
    public int paidPoint;
    public int paidValue;
    public int paidCard;
    public double paidOther;
    public String otherPayType;
    public int paidCoupon;
    public ThirdPayInfoBean thirdPayInfo;
    public String meno;
    public String thirdOpenId;
    public int deviceType;
    public String uniqueCode;
    public List<ConsumeListBean> consumeList;
    public List<CouponListBean> couponList;

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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public int getPaidPoint() {
        return paidPoint;
    }

    public void setPaidPoint(int paidPoint) {
        this.paidPoint = paidPoint;
    }

    public int getPaidValue() {
        return paidValue;
    }

    public void setPaidValue(int paidValue) {
        this.paidValue = paidValue;
    }

    public int getPaidCard() {
        return paidCard;
    }

    public void setPaidCard(int paidCard) {
        this.paidCard = paidCard;
    }

    public double getPaidOther() {
        return paidOther;
    }

    public void setPaidOther(double paidOther) {
        this.paidOther = paidOther;
    }

    public String getOtherPayType() {
        return otherPayType;
    }

    public void setOtherPayType(String otherPayType) {
        this.otherPayType = otherPayType;
    }

    public int getPaidCoupon() {
        return paidCoupon;
    }

    public void setPaidCoupon(int paidCoupon) {
        this.paidCoupon = paidCoupon;
    }

    public ThirdPayInfoBean getThirdPayInfo() {
        return thirdPayInfo;
    }

    public void setThirdPayInfo(ThirdPayInfoBean thirdPayInfo) {
        this.thirdPayInfo = thirdPayInfo;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getThirdOpenId() {
        return thirdOpenId;
    }

    public void setThirdOpenId(String thirdOpenId) {
        this.thirdOpenId = thirdOpenId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public List<ConsumeListBean> getConsumeList() {
        return consumeList;
    }

    public void setConsumeList(List<ConsumeListBean> consumeList) {
        this.consumeList = consumeList;
    }

    public List<CouponListBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponListBean> couponList) {
        this.couponList = couponList;
    }

    public static class ThirdPayInfoBean {
        /**
         * thirdPayType : 1
         * thirdPayValue : 4
         * authCode : 1346913505024158XX
         */
        //支付类型,1:微信,2:支付宝
        public String thirdPayType;
        //支付金额
        public String thirdPayValue;
        //付款码
        public String authCode;

        public String getThirdPayType() {
            return thirdPayType;
        }

        public void setThirdPayType(String thirdPayType) {
            this.thirdPayType = thirdPayType;
        }

        public String getThirdPayValue() {
            return thirdPayValue;
        }

        public void setThirdPayValue(String thirdPayValue) {
            this.thirdPayValue = thirdPayValue;
        }

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }
    }

    public static class ConsumeListBean {
        /**
         * （barcode：商品编码 （通过接口“获取商品列表”获得“Barcode”字段） ；
         * name：商品名称；
         * disCount：商品折扣；
         * number:商品数量；
         * paidMoney：该明细总应付金额；
         * meno：此明细的备注）
         * "consumeList":[] 或者不传consumeList,等同于快速消费
         * barcode : 0001
         * name : 123
         * number : 1
         * disCount : 0.8
         * paidMoney : 22
         * meno :
         */
        //商品编码 （通过接口“获取商品列表”获得“Barcode”字段） ；
        public String barcode;
        //商品名称
        public String name;
        //商品数量
        public int number;
        //商品折扣
        public double disCount;
        //该明细总应付金额
        public int paidMoney;
        //此明细的备注
        public String meno;

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public double getDisCount() {
            return disCount;
        }

        public void setDisCount(double disCount) {
            this.disCount = disCount;
        }

        public int getPaidMoney() {
            return paidMoney;
        }

        public void setPaidMoney(int paidMoney) {
            this.paidMoney = paidMoney;
        }

        public String getMeno() {
            return meno;
        }

        public void setMeno(String meno) {
            this.meno = meno;
        }
    }

    public static class CouponListBean {
        /**
         * 此参数为空时，paidCoupon无效
         * sendNoteGuid : AE3166F5-DEEF-4FCD-B984-0683329BAB63
         * count : 1
         */
        //优惠券发送记录唯一标识 （通过接口“获取已发送优惠券”获得“Guid”字段）
        public String sendNoteGuid;
        //核销数量
        public int count;

        public String getSendNoteGuid() {
            return sendNoteGuid;
        }

        public void setSendNoteGuid(String sendNoteGuid) {
            this.sendNoteGuid = sendNoteGuid;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
