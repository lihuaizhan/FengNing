package com.example.tps900.tps900.MenberBean;

import java.util.List;

/**
 * 项目名称：PDA_FengNing
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-06-23 17:05
 * 修改人：zxh
 * 修改时间：2018-06-23 17:05
 * 修改备注：
 */

public class ReQueryMemberInfo {


    /**
     * status : 0
     * data : [{"CardId":"q123","Sex":1,"TrueName":"张三","Mobile":"13632651195","Tel":"","Birthday":null,"IsLunar":false,"UserAccount":"10000","StoreName":"总部","ImagePath":"http://files.sz1card1.com/3573/201404/201404151019202088.jpeg","MemberGroupName":"时尚金卡","RecommendMemberCardId":"q","RecommendMemberName":"小李","RegisterTime":"2016-12-31","DurationTime":"2014-01-10","Meno":"重要客户","ProvinceId":4,"ProvinceName":"山西","CityId":23,"CityName":"吕梁","CountyId":338,"CountyName":"文水县","Address":"小李庄","TotalPoint":"794161.47","TotalValue":"7505.00","EnablePoint":"7051.25","EnableValue":"12179.65","TotalMoney":"28338.60","FreezedValue":"100.00"}]
     * extValue : [{"车牌号":"98765","星座":"金牛座"}]
     */

    public int status;
    public String message;
    public List<DataBean> data;
    public List<ExtValueBean> extValue;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<ExtValueBean> getExtValue() {
        return extValue;
    }

    public void setExtValue(List<ExtValueBean> extValue) {
        this.extValue = extValue;
    }

    public static class DataBean {
        /**
         * CardId : q123
         * Sex : 1
         * TrueName : 张三
         * Mobile : 13632651195
         * Tel :
         * Birthday : null
         * IsLunar : false
         * UserAccount : 10000
         * StoreName : 总部
         * ImagePath : http://files.sz1card1.com/3573/201404/201404151019202088.jpeg
         * MemberGroupName : 时尚金卡
         * RecommendMemberCardId : q
         * RecommendMemberName : 小李
         * RegisterTime : 2016-12-31
         * DurationTime : 2014-01-10
         * Meno : 重要客户
         * ProvinceId : 4
         * ProvinceName : 山西
         * CityId : 23
         * CityName : 吕梁
         * CountyId : 338
         * CountyName : 文水县
         * Address : 小李庄
         * TotalPoint : 794161.47
         * TotalValue : 7505.00
         * EnablePoint : 7051.25
         * EnableValue : 12179.65
         * TotalMoney : 28338.60
         * FreezedValue : 100.00
         */

        public String CardId;
        public int Sex;
        public String TrueName;
        public String Mobile;
        public String Tel;
        public Object Birthday;
        public boolean IsLunar;
        public String UserAccount;
        public String StoreName;
        public String ImagePath;
        public String MemberGroupName;
        public String RecommendMemberCardId;
        public String RecommendMemberName;
        public String RegisterTime;
        public String DurationTime;
        public String Meno;
        public int ProvinceId;
        public String ProvinceName;
        public int CityId;
        public String CityName;
        public int CountyId;
        public String CountyName;
        public String Address;
        public String TotalPoint;
        public String TotalValue;
        public String EnablePoint;
        public String EnableValue;
        public String TotalMoney;
        public String FreezedValue;

        public String getCardId() {
            return CardId;
        }

        public void setCardId(String cardId) {
            CardId = cardId;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int sex) {
            Sex = sex;
        }

        public String getTrueName() {
            return TrueName;
        }

        public void setTrueName(String trueName) {
            TrueName = trueName;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public Object getBirthday() {
            return Birthday;
        }

        public void setBirthday(Object birthday) {
            Birthday = birthday;
        }

        public boolean isLunar() {
            return IsLunar;
        }

        public void setLunar(boolean lunar) {
            IsLunar = lunar;
        }

        public String getUserAccount() {
            return UserAccount;
        }

        public void setUserAccount(String userAccount) {
            UserAccount = userAccount;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String storeName) {
            StoreName = storeName;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }

        public String getMemberGroupName() {
            return MemberGroupName;
        }

        public void setMemberGroupName(String memberGroupName) {
            MemberGroupName = memberGroupName;
        }

        public String getRecommendMemberCardId() {
            return RecommendMemberCardId;
        }

        public void setRecommendMemberCardId(String recommendMemberCardId) {
            RecommendMemberCardId = recommendMemberCardId;
        }

        public String getRecommendMemberName() {
            return RecommendMemberName;
        }

        public void setRecommendMemberName(String recommendMemberName) {
            RecommendMemberName = recommendMemberName;
        }

        public String getRegisterTime() {
            return RegisterTime;
        }

        public void setRegisterTime(String registerTime) {
            RegisterTime = registerTime;
        }

        public String getDurationTime() {
            return DurationTime;
        }

        public void setDurationTime(String durationTime) {
            DurationTime = durationTime;
        }

        public String getMeno() {
            return Meno;
        }

        public void setMeno(String meno) {
            Meno = meno;
        }

        public int getProvinceId() {
            return ProvinceId;
        }

        public void setProvinceId(int provinceId) {
            ProvinceId = provinceId;
        }

        public String getProvinceName() {
            return ProvinceName;
        }

        public void setProvinceName(String provinceName) {
            ProvinceName = provinceName;
        }

        public int getCityId() {
            return CityId;
        }

        public void setCityId(int cityId) {
            CityId = cityId;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String cityName) {
            CityName = cityName;
        }

        public int getCountyId() {
            return CountyId;
        }

        public void setCountyId(int countyId) {
            CountyId = countyId;
        }

        public String getCountyName() {
            return CountyName;
        }

        public void setCountyName(String countyName) {
            CountyName = countyName;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getTotalPoint() {
            return TotalPoint;
        }

        public void setTotalPoint(String totalPoint) {
            TotalPoint = totalPoint;
        }

        public String getTotalValue() {
            return TotalValue;
        }

        public void setTotalValue(String totalValue) {
            TotalValue = totalValue;
        }

        public String getEnablePoint() {
            return EnablePoint;
        }

        public void setEnablePoint(String enablePoint) {
            EnablePoint = enablePoint;
        }

        public String getEnableValue() {
            return EnableValue;
        }

        public void setEnableValue(String enableValue) {
            EnableValue = enableValue;
        }

        public String getTotalMoney() {
            return TotalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            TotalMoney = totalMoney;
        }

        public String getFreezedValue() {
            return FreezedValue;
        }

        public void setFreezedValue(String freezedValue) {
            FreezedValue = freezedValue;
        }
    }

    public static class ExtValueBean {
        /**
         * 车牌号 : 98765
         * 星座 : 金牛座
         */

        public String 车牌号;
        public String 星座;


    }


    @Override
    public String toString() {
        return "ReQueryMemberInfo{" +
                "status=" + status +
                ", data=" + data +
                ", extValue=" + extValue +
                '}';
    }
}
