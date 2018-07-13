package com.example.tps900.tps900.Bean;

import java.util.List;


/**
 * 项目名称：Galasys_TPS610
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/7/5 10:41
 * 修改人：zxh
 * 修改时间：2017/7/5 10:41
 * 修改备注：
 */

public class GetLevelInfo_Bean {

    /**
     * Data : [{"LevelId":"0000000105","LevelName":"银卡","IsStored":"1","IsVipMark":"1","IsAdJust":"0","DepositAmount":null,"DepositItid":null,"RentAmount":null,"RentItid":null,"ReissueAmount":null,"ReissueItid":null},{"LevelId":"0000000081","LevelName":"蓝卡","IsStored":"1","IsVipMark":"1","IsAdJust":"0","DepositAmount":null,"DepositItid":null,"RentAmount":null,"RentItid":null,"ReissueAmount":null,"ReissueItid":null},{"LevelId":"0000000117","LevelName":"金卡","IsStored":"1","IsVipMark":"1","IsAdJust":"1","DepositAmount":null,"DepositItid":null,"RentAmount":null,"RentItid":null,"ReissueAmount":null,"ReissueItid":null},{"LevelId":"0000000118","LevelName":"钻石卡","IsStored":"1","IsVipMark":"1","IsAdJust":"1","DepositAmount":null,"DepositItid":null,"RentAmount":null,"RentItid":null,"ReissueAmount":null,"ReissueItid":null},{"LevelId":"0000000122","LevelName":"OTA会员卡","IsStored":"1","IsVipMark":"1","IsAdJust":"1","DepositAmount":null,"DepositItid":null,"RentAmount":null,"RentItid":null,"ReissueAmount":null,"ReissueItid":null},{"LevelId":"0000000134","LevelName":"微信/官网","IsStored":"0","IsVipMark":"0","IsAdJust":"0","DepositAmount":null,"DepositItid":null,"RentAmount":null,"RentItid":null,"ReissueAmount":null,"ReissueItid":null},{"LevelId":"0000000135","LevelName":"微信/官网","IsStored":null,"IsVipMark":null,"IsAdJust":null,"DepositAmount":null,"DepositItid":null,"RentAmount":null,"RentItid":null,"ReissueAmount":null,"ReissueItid":null},{"LevelId":"0000000136","LevelName":"微信/官网","IsStored":null,"IsVipMark":null,"IsAdJust":null,"DepositAmount":null,"DepositItid":null,"RentAmount":null,"RentItid":null,"ReissueAmount":null,"ReissueItid":null}]
     * Success : true
     * Message : null
     */

    public boolean Success;
    public String Message;
    public List<DataBean> Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> data) {
        Data = data;
    }

    public static class DataBean {
        /**
         * LevelId : 0000000105
         * LevelName : 银卡
         * IsStored : 1
         * IsVipMark : 1
         * IsAdJust : 0
         * DepositAmount : null
         * DepositItid : null
         * RentAmount : null
         * RentItid : null
         * ReissueAmount : null
         * ReissueItid : null
         */

        public String LevelId;
        public String LevelName;
        public String IsStored;
        public String IsVipMark;
        public String IsAdJust;
        public String DepositAmount;
        public String DepositItid;
        public String RentAmount;
        public String RentItid;
        public String ReissueAmount;
        public String ReissueItid;
        public double INTEGRALRATE;//积分率
        public double DEPOSITRATE;//折扣率

        public double getINTEGRALRATE() {
            return INTEGRALRATE;
        }

        public void setINTEGRALRATE(double INTEGRALRATE) {
            this.INTEGRALRATE = INTEGRALRATE;
        }

        public double getDEPOSITRATE() {
            return DEPOSITRATE;
        }

        public void setDEPOSITRATE(double DEPOSITRATE) {
            this.DEPOSITRATE = DEPOSITRATE;
        }

        public String getLevelId() {
            return LevelId;
        }

        public void setLevelId(String levelId) {
            LevelId = levelId;
        }

        public String getLevelName() {
            return LevelName;
        }

        public void setLevelName(String levelName) {
            LevelName = levelName;
        }

        public String getIsStored() {
            return IsStored;
        }

        public void setIsStored(String isStored) {
            IsStored = isStored;
        }

        public String getIsVipMark() {
            return IsVipMark;
        }

        public void setIsVipMark(String isVipMark) {
            IsVipMark = isVipMark;
        }

        public String getIsAdJust() {
            return IsAdJust;
        }

        public void setIsAdJust(String isAdJust) {
            IsAdJust = isAdJust;
        }

        public String getDepositAmount() {
            return DepositAmount;
        }

        public void setDepositAmount(String depositAmount) {
            DepositAmount = depositAmount;
        }

        public String getDepositItid() {
            return DepositItid;
        }

        public void setDepositItid(String depositItid) {
            DepositItid = depositItid;
        }

        public String getRentAmount() {
            return RentAmount;
        }

        public void setRentAmount(String rentAmount) {
            RentAmount = rentAmount;
        }

        public String getRentItid() {
            return RentItid;
        }

        public void setRentItid(String rentItid) {
            RentItid = rentItid;
        }

        public String getReissueAmount() {
            return ReissueAmount;
        }

        public void setReissueAmount(String reissueAmount) {
            ReissueAmount = reissueAmount;
        }

        public String getReissueItid() {
            return ReissueItid;
        }

        public void setReissueItid(String reissueItid) {
            ReissueItid = reissueItid;
        }
    }
}
