package com.example.tps900.tps900.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wth on 2017/7/6.
 */
public class GetVipInfoTogether_been implements Serializable {
    /**
     * Data : [{"VipInfo":{"VipId":"VIP00000176","CardNo":"VIP00000176","ParkId":"00000205","CardNum":null,"CardId":null,"CardLevelId":"0000000105","CardLevelName":"银卡","VipName":"王晋","VipSex":"0","VipPhone":"18583299020","VipAddress":"四川","VipChannelId":null,"VipChannelName":null,"VipCertificatesNo":null,"VipBirthday":"2017-12-01 00:00:00","VipNation":null,"VipNationlity":null,"VipQQ":null,"VipEmail":null,"VipWeChat":null,"VipNickName":"青枳.","CardType":"1"},"VipBalance":[{"VipId":"VIP00000176","AccountId":"00000361","AccountName":"现金","Balance":3280.93,"IsConsume":"0","IsCost":"1","IsReturn":"1"},{"VipId":"VIP00000176","AccountId":"00000363","AccountName":"赠金","Balance":50,"IsConsume":"0","IsCost":"1","IsReturn":"0"}],"VipMark":[{"Vipid":"VIP00000176","VipName":"王晋","VipPhone":"18583299020","Balance":2,"SumAmount":2}]}]
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

    public static class DataBean implements Serializable {
        /**
         * VipInfo : {"VipId":"VIP00000176","CardNo":"VIP00000176","ParkId":"00000205","CardNum":null,"CardId":null,"CardLevelId":"0000000105","CardLevelName":"银卡","VipName":"王晋","VipSex":"0","VipPhone":"18583299020","VipAddress":"四川","VipChannelId":null,"VipChannelName":null,"VipCertificatesNo":null,"VipBirthday":"2017-12-01 00:00:00","VipNation":null,"VipNationlity":null,"VipQQ":null,"VipEmail":null,"VipWeChat":null,"VipNickName":"青枳.","CardType":"1"}
         * VipBalance : [{"VipId":"VIP00000176","AccountId":"00000361","AccountName":"现金","Balance":3280.93,"IsConsume":"0","IsCost":"1","IsReturn":"1"},{"VipId":"VIP00000176","AccountId":"00000363","AccountName":"赠金","Balance":50,"IsConsume":"0","IsCost":"1","IsReturn":"0"}]
         * VipMark : [{"Vipid":"VIP00000176","VipName":"王晋","VipPhone":"18583299020","Balance":2,"SumAmount":2}]
         */

        public VipInfoBean VipInfo;
        public List<VipBalanceBean> VipBalance;
        public List<VipMarkBean> VipMark;
        public String MachineBalance;
        public String Balance;

        public String getBalance() {
            return Balance;
        }

        public void setBalance(String balance) {
            Balance = balance;
        }

        public String getMachineBalance() {
            return MachineBalance;
        }

        public void setMachineBalance(String machineBalance) {
            MachineBalance = machineBalance;
        }

        public VipInfoBean getVipInfo() {
            return VipInfo;
        }

        public void setVipInfo(VipInfoBean vipInfo) {
            VipInfo = vipInfo;
        }

        public List<VipBalanceBean> getVipBalance() {
            return VipBalance;
        }

        public void setVipBalance(List<VipBalanceBean> vipBalance) {
            VipBalance = vipBalance;
        }

        public List<VipMarkBean> getVipMark() {
            return VipMark;
        }

        public void setVipMark(List<VipMarkBean> vipMark) {
            VipMark = vipMark;
        }

        public static class VipInfoBean implements Serializable {
            /**
             * VipId : VIP00000176
             * CardNo : VIP00000176
             * ParkId : 00000205
             * CardNum : null
             * CardId : null
             * CardLevelId : 0000000105
             * CardLevelName : 银卡
             * VipName : 王晋
             * VipSex : 0
             * VipPhone : 18583299020
             * VipAddress : 四川
             * VipChannelId : null
             * VipChannelName : null
             * VipCertificatesNo : null
             * VipBirthday : 2017-12-01 00:00:00
             * VipNation : null
             * VipNationlity : null
             * VipQQ : null
             * VipEmail : null
             * VipWeChat : null
             * VipNickName : 青枳.
             * CardType : 1
             * PicUrl
             */

            public String VipId;
            public String CardNo;
            public String ParkId;
            public String CardNum;
            public String CardId;
            public String CardLevelId;
            public String CardLevelName;
            public String VipName;
            public String VipSex;
            public String VipPhone;
            public String VipAddress;
            public String VipChannelId;
            public String VipChannelName;
            public String VipCertificatesNo;
            public String VipBirthday;
            public String VipNation;
            public String VipNationlity;
            public String VipQQ;
            public String VipEmail;
            public String VipWeChat;
            public String VipNickName;
            public String CardType;
            public String reissueItid;
            private String PicUrl;

            public void setPicUrl(String picUrl) {
                PicUrl = picUrl;
            }

            public String getPicUrl() {
                return PicUrl;
            }

            public String getVipId() {
                return VipId;
            }

            public void setVipId(String vipId) {
                VipId = vipId;
            }

            public String getCardNo() {
                return CardNo;
            }

            public void setCardNo(String cardNo) {
                CardNo = cardNo;
            }

            public String getParkId() {
                return ParkId;
            }

            public void setParkId(String parkId) {
                ParkId = parkId;
            }

            public String getCardNum() {
                return CardNum;
            }

            public void setCardNum(String cardNum) {
                CardNum = cardNum;
            }

            public String getCardId() {
                return CardId;
            }

            public void setCardId(String cardId) {
                CardId = cardId;
            }

            public String getCardLevelId() {
                return CardLevelId;
            }

            public void setCardLevelId(String cardLevelId) {
                CardLevelId = cardLevelId;
            }

            public String getCardLevelName() {
                return CardLevelName;
            }

            public void setCardLevelName(String cardLevelName) {
                CardLevelName = cardLevelName;
            }

            public String getVipName() {
                return VipName;
            }

            public void setVipName(String vipName) {
                VipName = vipName;
            }

            public String getVipSex() {
                return VipSex;
            }

            public void setVipSex(String vipSex) {
                VipSex = vipSex;
            }

            public String getVipPhone() {
                return VipPhone;
            }

            public void setVipPhone(String vipPhone) {
                VipPhone = vipPhone;
            }

            public String getVipAddress() {
                return VipAddress;
            }

            public void setVipAddress(String vipAddress) {
                VipAddress = vipAddress;
            }

            public String getVipChannelId() {
                return VipChannelId;
            }

            public void setVipChannelId(String vipChannelId) {
                VipChannelId = vipChannelId;
            }

            public String getVipChannelName() {
                return VipChannelName;
            }

            public void setVipChannelName(String vipChannelName) {
                VipChannelName = vipChannelName;
            }

            public String getVipCertificatesNo() {
                return VipCertificatesNo;
            }

            public void setVipCertificatesNo(String vipCertificatesNo) {
                VipCertificatesNo = vipCertificatesNo;
            }

            public String getVipBirthday() {
                return VipBirthday;
            }

            public void setVipBirthday(String vipBirthday) {
                VipBirthday = vipBirthday;
            }

            public String getVipNation() {
                return VipNation;
            }

            public void setVipNation(String vipNation) {
                VipNation = vipNation;
            }

            public String getVipNationlity() {
                return VipNationlity;
            }

            public void setVipNationlity(String vipNationlity) {
                VipNationlity = vipNationlity;
            }

            public String getVipQQ() {
                return VipQQ;
            }

            public void setVipQQ(String vipQQ) {
                VipQQ = vipQQ;
            }

            public String getVipEmail() {
                return VipEmail;
            }

            public void setVipEmail(String vipEmail) {
                VipEmail = vipEmail;
            }

            public String getVipWeChat() {
                return VipWeChat;
            }

            public void setVipWeChat(String vipWeChat) {
                VipWeChat = vipWeChat;
            }

            public String getVipNickName() {
                return VipNickName;
            }

            public void setVipNickName(String vipNickName) {
                VipNickName = vipNickName;
            }

            public String getCardType() {
                return CardType;
            }

            public void setCardType(String cardType) {
                CardType = cardType;
            }

            public void setReissueItid(String reissueItid) {
                this.reissueItid = reissueItid;
            }

            public String getReissueItid() {
                return reissueItid;
            }
        }

        public static class VipBalanceBean implements Serializable {
            /**
             * VipId : VIP00000176
             * AccountId : 00000361
             * AccountName : 现金
             * Balance : 3280.93
             * IsConsume : 0
             * IsCost : 1
             * IsReturn : 1
             */

            public String VipId;
            public String AccountId;
            public String AccountName;
            public double Balance;
            public String IsConsume;
            public String IsCost;
            public String IsReturn;

            public String getVipId() {

                return VipId;
            }

            public void setVipId(String vipId) {
                VipId = vipId;
            }

            public String getAccountId() {
                return AccountId;
            }

            public void setAccountId(String accountId) {
                AccountId = accountId;
            }

            public String getAccountName() {
                return AccountName;
            }

            public void setAccountName(String accountName) {
                AccountName = accountName;
            }

            public double getBalance() {
                return Balance;
            }

            public void setBalance(double balance) {
                Balance = balance;
            }

            public String getIsConsume() {
                return IsConsume;
            }

            public void setIsConsume(String isConsume) {
                IsConsume = isConsume;
            }

            public String getIsCost() {
                return IsCost;
            }

            public void setIsCost(String isCost) {
                IsCost = isCost;
            }

            public String getIsReturn() {
                return IsReturn;
            }

            public void setIsReturn(String isReturn) {
                IsReturn = isReturn;
            }
        }

        public static class VipMarkBean implements Serializable {
            /**
             * Vipid : VIP00000176
             * VipName : 王晋
             * VipPhone : 18583299020
             * Balance : 2
             * SumAmount : 2
             */

            public String Vipid;
            public String VipName;
            public String VipPhone;
            public double Balance;
            public int SumAmount;

            public String getVipid() {
                return Vipid;
            }

            public void setVipid(String vipid) {
                Vipid = vipid;
            }

            public String getVipName() {
                return VipName;
            }

            public void setVipName(String vipName) {
                VipName = vipName;
            }

            public String getVipPhone() {
                return VipPhone;
            }

            public void setVipPhone(String vipPhone) {
                VipPhone = vipPhone;
            }

            public double getBalance() {
                return Balance;
            }

            public void setBalance(int balance) {
                Balance = balance;
            }

            public int getSumAmount() {
                return SumAmount;
            }

            public void setSumAmount(int sumAmount) {
                SumAmount = sumAmount;
            }
        }
    }
}
