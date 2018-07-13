package com.example.tps900.tps900.Bean;

import java.util.List;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-02-02 20:04
 * 修改人：zxh
 * 修改时间：2018-02-02 20:04
 * 修改备注：
 */

public class TeamBwan_2 {

    /**
     * Data : {"TeamOrders":[{"ItemsId":"00002729","OrderId":"00001912","OrderNo":"T00001912","ProductId":"00004132","ProductName":"极地海洋馆","verificationNum":null,"ProductPrice":"0.01","performanceDate":null,"ticketName":null,"identityCard":null,"ProductNum":"3","ProPrintPric":"0.01","Ecode":"122431715441","titems_ecode":null,"PlayDate":"2018/2/3 0:00:00","sdate":null,"edate":null,"stime":null,"etime":null,"PartnerName":"存存旅行社","OrderAmt":"0.03","PayType":"0","ParkId":"00000256","Remark":null,"IsPay":"0","IsPrint":"0","OffCode":null,"idcardt":null,"idcardt_seatid":null,"idcardt_xcol":null,"idcardt_yrow":null}],"TeamGuides":[{"GuideName":"撒打","GuidePhone":"13643281595","CardNo":"111111111111111111"}],"TeamDriver":[{"TDRIVER_NAME":"撒大声地","TDRIVER_CARTNO":"GK12007"}],"TeamTheatres":null}
     * Success : true
     * Code : null
     * Message : null
     * dtTime : null
     * Orderno : null
     */

    public DataBean Data;
    public boolean Success;
    public String Code;
    public String Message;
    public String dtTime;
    public String Orderno;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean data) {
        Data = data;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDtTime() {
        return dtTime;
    }

    public void setDtTime(String dtTime) {
        this.dtTime = dtTime;
    }

    public String getOrderno() {
        return Orderno;
    }

    public void setOrderno(String orderno) {
        Orderno = orderno;
    }

    public static class DataBean {
        /**
         * TeamOrders : [{"ItemsId":"00002729","OrderId":"00001912","OrderNo":"T00001912","ProductId":"00004132","ProductName":"极地海洋馆","verificationNum":null,"ProductPrice":"0.01","performanceDate":null,"ticketName":null,"identityCard":null,"ProductNum":"3","ProPrintPric":"0.01","Ecode":"122431715441","titems_ecode":null,"PlayDate":"2018/2/3 0:00:00","sdate":null,"edate":null,"stime":null,"etime":null,"PartnerName":"存存旅行社","OrderAmt":"0.03","PayType":"0","ParkId":"00000256","Remark":null,"IsPay":"0","IsPrint":"0","OffCode":null,"idcardt":null,"idcardt_seatid":null,"idcardt_xcol":null,"idcardt_yrow":null}]
         * TeamGuides : [{"GuideName":"撒打","GuidePhone":"13643281595","CardNo":"111111111111111111"}]
         * TeamDriver : [{"TDRIVER_NAME":"撒大声地","TDRIVER_CARTNO":"GK12007"}]
         * TeamTheatres : null
         */

        public String TeamTheatres;
        public List<TeamOrdersBean> TeamOrders;
        public List<TeamGuidesBean> TeamGuides;
        public List<TeamDriverBean> TeamDriver;

        public String getTeamTheatres() {
            return TeamTheatres;
        }

        public void setTeamTheatres(String teamTheatres) {
            TeamTheatres = teamTheatres;
        }

        public List<TeamOrdersBean> getTeamOrders() {
            return TeamOrders;
        }

        public void setTeamOrders(List<TeamOrdersBean> teamOrders) {
            TeamOrders = teamOrders;
        }

        public List<TeamGuidesBean> getTeamGuides() {
            return TeamGuides;
        }

        public void setTeamGuides(List<TeamGuidesBean> teamGuides) {
            TeamGuides = teamGuides;
        }

        public List<TeamDriverBean> getTeamDriver() {
            return TeamDriver;
        }

        public void setTeamDriver(List<TeamDriverBean> teamDriver) {
            TeamDriver = teamDriver;
        }

        public static class TeamOrdersBean {
            /**
             * ItemsId : 00002729
             * OrderId : 00001912
             * OrderNo : T00001912
             * ProductId : 00004132
             * ProductName : 极地海洋馆
             * verificationNum : null
             * ProductPrice : 0.01
             * performanceDate : null
             * ticketName : null
             * identityCard : null
             * ProductNum : 3
             * ProPrintPric : 0.01
             * Ecode : 122431715441
             * titems_ecode : null
             * PlayDate : 2018/2/3 0:00:00
             * sdate : null
             * edate : null
             * stime : null
             * etime : null
             * PartnerName : 存存旅行社
             * OrderAmt : 0.03
             * PayType : 0
             * ParkId : 00000256
             * Remark : null
             * IsPay : 0
             * IsPrint : 0
             * OffCode : null
             * idcardt : null
             * idcardt_seatid : null
             * idcardt_xcol : null
             * idcardt_yrow : null
             */

            public String ItemsId;
            public String OrderId;
            public String OrderNo;
            public String ProductId;
            public String ProductName;
            public String verificationNum;
            public String ProductPrice;
            public String performanceDate;
            public String ticketName;
            public String identityCard;
            public String ProductNum;
            public String ProPrintPric;
            public String Ecode;
            public String titems_ecode;
            public String PlayDate;
            public String sdate;
            public String edate;
            public String stime;
            public String etime;
            public String PartnerName;
            public String OrderAmt;
            public String PayType;
            public String ParkId;
            public String Remark;
            public String IsPay;
            public String IsPrint;
            public String OffCode;
            public String idcardt;
            public String idcardt_seatid;
            public String idcardt_xcol;
            public String idcardt_yrow;

            public String getItemsId() {
                return ItemsId;
            }

            public void setItemsId(String itemsId) {
                ItemsId = itemsId;
            }

            public String getOrderId() {
                return OrderId;
            }

            public void setOrderId(String orderId) {
                OrderId = orderId;
            }

            public String getOrderNo() {
                return OrderNo;
            }

            public void setOrderNo(String orderNo) {
                OrderNo = orderNo;
            }

            public String getProductId() {
                return ProductId;
            }

            public void setProductId(String productId) {
                ProductId = productId;
            }

            public String getProductName() {
                return ProductName;
            }

            public void setProductName(String productName) {
                ProductName = productName;
            }

            public String getVerificationNum() {
                return verificationNum;
            }

            public void setVerificationNum(String verificationNum) {
                this.verificationNum = verificationNum;
            }

            public String getProductPrice() {
                return ProductPrice;
            }

            public void setProductPrice(String productPrice) {
                ProductPrice = productPrice;
            }

            public String getPerformanceDate() {
                return performanceDate;
            }

            public void setPerformanceDate(String performanceDate) {
                this.performanceDate = performanceDate;
            }

            public String getTicketName() {
                return ticketName;
            }

            public void setTicketName(String ticketName) {
                this.ticketName = ticketName;
            }

            public String getIdentityCard() {
                return identityCard;
            }

            public void setIdentityCard(String identityCard) {
                this.identityCard = identityCard;
            }

            public String getProductNum() {
                return ProductNum;
            }

            public void setProductNum(String productNum) {
                ProductNum = productNum;
            }

            public String getProPrintPric() {
                return ProPrintPric;
            }

            public void setProPrintPric(String proPrintPric) {
                ProPrintPric = proPrintPric;
            }

            public String getEcode() {
                return Ecode;
            }

            public void setEcode(String ecode) {
                Ecode = ecode;
            }

            public String getTitems_ecode() {
                return titems_ecode;
            }

            public void setTitems_ecode(String titems_ecode) {
                this.titems_ecode = titems_ecode;
            }

            public String getPlayDate() {
                return PlayDate;
            }

            public void setPlayDate(String playDate) {
                PlayDate = playDate;
            }

            public String getSdate() {
                return sdate;
            }

            public void setSdate(String sdate) {
                this.sdate = sdate;
            }

            public String getEdate() {
                return edate;
            }

            public void setEdate(String edate) {
                this.edate = edate;
            }

            public String getStime() {
                return stime;
            }

            public void setStime(String stime) {
                this.stime = stime;
            }

            public String getEtime() {
                return etime;
            }

            public void setEtime(String etime) {
                this.etime = etime;
            }

            public String getPartnerName() {
                return PartnerName;
            }

            public void setPartnerName(String partnerName) {
                PartnerName = partnerName;
            }

            public String getOrderAmt() {
                return OrderAmt;
            }

            public void setOrderAmt(String orderAmt) {
                OrderAmt = orderAmt;
            }

            public String getPayType() {
                return PayType;
            }

            public void setPayType(String payType) {
                PayType = payType;
            }

            public String getParkId() {
                return ParkId;
            }

            public void setParkId(String parkId) {
                ParkId = parkId;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String remark) {
                Remark = remark;
            }

            public String getIsPay() {
                return IsPay;
            }

            public void setIsPay(String isPay) {
                IsPay = isPay;
            }

            public String getIsPrint() {
                return IsPrint;
            }

            public void setIsPrint(String isPrint) {
                IsPrint = isPrint;
            }

            public String getOffCode() {
                return OffCode;
            }

            public void setOffCode(String offCode) {
                OffCode = offCode;
            }

            public String getIdcardt() {
                return idcardt;
            }

            public void setIdcardt(String idcardt) {
                this.idcardt = idcardt;
            }

            public String getIdcardt_seatid() {
                return idcardt_seatid;
            }

            public void setIdcardt_seatid(String idcardt_seatid) {
                this.idcardt_seatid = idcardt_seatid;
            }

            public String getIdcardt_xcol() {
                return idcardt_xcol;
            }

            public void setIdcardt_xcol(String idcardt_xcol) {
                this.idcardt_xcol = idcardt_xcol;
            }

            public String getIdcardt_yrow() {
                return idcardt_yrow;
            }

            public void setIdcardt_yrow(String idcardt_yrow) {
                this.idcardt_yrow = idcardt_yrow;
            }
        }

        public static class TeamGuidesBean {
            /**
             * GuideName : 撒打
             * GuidePhone : 13643281595
             * CardNo : 111111111111111111
             */

            public String GuideName;
            public String GuidePhone;
            public String CardNo;

            public String getGuideName() {
                return GuideName;
            }

            public void setGuideName(String guideName) {
                GuideName = guideName;
            }

            public String getGuidePhone() {
                return GuidePhone;
            }

            public void setGuidePhone(String guidePhone) {
                GuidePhone = guidePhone;
            }

            public String getCardNo() {
                return CardNo;
            }

            public void setCardNo(String cardNo) {
                CardNo = cardNo;
            }
        }

        public static class TeamDriverBean {
            /**
             * TDRIVER_NAME : 撒大声地
             * TDRIVER_CARTNO : GK12007
             */

            public String TDRIVER_NAME;
            public String TDRIVER_CARTNO;

            public String getTDRIVER_NAME() {
                return TDRIVER_NAME;
            }

            public void setTDRIVER_NAME(String TDRIVER_NAME) {
                this.TDRIVER_NAME = TDRIVER_NAME;
            }

            public String getTDRIVER_CARTNO() {
                return TDRIVER_CARTNO;
            }

            public void setTDRIVER_CARTNO(String TDRIVER_CARTNO) {
                this.TDRIVER_CARTNO = TDRIVER_CARTNO;
            }
        }
    }
}
