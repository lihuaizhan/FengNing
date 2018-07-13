package com.example.tps900.tps900.Bean;

import java.util.List;


/**
 * Created by wth on 2017/7/6.
 */
public class GetVipRate_Bean {

    /**
     * Data : [{"LevelId":"0000000105","LevelName":"银卡","INTEGRALRATE":0.7,"DEPOSITRATE":0.7,"Memo":null},{"LevelId":"0000000081","LevelName":"蓝卡","INTEGRALRATE":0.5,"DEPOSITRATE":0.5,"Memo":null}]
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
         * INTEGRALRATE : 0.7//积分率
         * DEPOSITRATE : 0.7//折扣率
         * Memo : null
         */

        public String LevelId;
        public String LevelName;
        public double INTEGRALRATE;
        public double DEPOSITRATE;
        public String Memo;


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

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String memo) {
            Memo = memo;
        }
    }
}
