package com.example.tps900.tps900.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：PDAZHB
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-05-29 18:02
 * 修改人：zxh
 * 修改时间：2018-05-29 18:02
 * 修改备注：
 */

public class RePrintBean {
    //补打类别
    public List<String> RePrintType;
    //核销补打上笔
    public TicketPrintBean ticketPintList;
    //商品补打上笔
    public RePrintGoodBean rePrintGoodBeanList;
    //商品退货补打上笔
    public RePrintGoodBean rePrintGoodReturnBeanList;
    //餐饮补打上笔
    public RePrintGoodBean rePrintFoodBeanList;
    //餐饮退货补打上笔
    public RePrintGoodBean rePrintFoodReturnBeanList;
    //线下门票售卖
    public Project_feeBean RePrintProject_feeBean;
    //线下门票售卖总价
    public double RePrintTicketMoney;
    //消费支付
    public ConsumptionAndPayment consumptionAndPayment;
    //散客兑票时间
    public String fitTime;
    //散客兑票
    public ArrayList<GoodsInfo> fitTicketList;
    //团队兑票
    public List<TeamBean_1.DataBean.TeamOrdersBean> teamOrders;
    //团队支付总额
    public String teamMoney;
    //团队兑票时间
    public String teamTime;

    public String getFitTime() {
        return fitTime;
    }

    public void setFitTime(String fitTime) {
        this.fitTime = fitTime;
    }

    public String getTeamTime() {
        return teamTime;
    }

    public void setTeamTime(String teamTime) {
        this.teamTime = teamTime;
    }

    public ArrayList<GoodsInfo> getFitTicketList() {
        return fitTicketList;
    }

    public void setFitTicketList(ArrayList<GoodsInfo> fitTicketList) {
        this.fitTicketList = fitTicketList;
    }

    public List<TeamBean_1.DataBean.TeamOrdersBean> getTeamOrders() {
        return teamOrders;
    }

    public void setTeamOrders(List<TeamBean_1.DataBean.TeamOrdersBean> teamOrders) {
        this.teamOrders = teamOrders;
    }

    public String getTeamMoney() {
        return teamMoney;
    }

    public void setTeamMoney(String teamMoney) {
        this.teamMoney = teamMoney;
    }

    public ConsumptionAndPayment getConsumptionAndPayment() {
        return consumptionAndPayment;
    }

    public void setConsumptionAndPayment(ConsumptionAndPayment consumptionAndPayment) {
        this.consumptionAndPayment = consumptionAndPayment;
    }

    public List<String> getRePrintType() {
        return RePrintType;
    }

    public void setRePrintType(List<String> rePrintType) {
        RePrintType = rePrintType;
    }

    public TicketPrintBean getTicketPintList() {
        return ticketPintList;
    }

    public void setTicketPintList(TicketPrintBean ticketPintList) {
        this.ticketPintList = ticketPintList;
    }

    public RePrintGoodBean getRePrintGoodBeanList() {
        return rePrintGoodBeanList;
    }

    public void setRePrintGoodBeanList(RePrintGoodBean rePrintGoodBeanList) {
        this.rePrintGoodBeanList = rePrintGoodBeanList;
    }

    public RePrintGoodBean getRePrintGoodReturnBeanList() {
        return rePrintGoodReturnBeanList;
    }

    public void setRePrintGoodReturnBeanList(RePrintGoodBean rePrintGoodReturnBeanList) {
        this.rePrintGoodReturnBeanList = rePrintGoodReturnBeanList;
    }

    public RePrintGoodBean getRePrintFoodBeanList() {
        return rePrintFoodBeanList;
    }

    public void setRePrintFoodBeanList(RePrintGoodBean rePrintFoodBeanList) {
        this.rePrintFoodBeanList = rePrintFoodBeanList;
    }

    public RePrintGoodBean getRePrintFoodReturnBeanList() {
        return rePrintFoodReturnBeanList;
    }

    public void setRePrintFoodReturnBeanList(RePrintGoodBean rePrintFoodReturnBeanList) {
        this.rePrintFoodReturnBeanList = rePrintFoodReturnBeanList;
    }

    public Project_feeBean getRePrintProject_feeBean() {
        return RePrintProject_feeBean;
    }

    public void setRePrintProject_feeBean(Project_feeBean rePrintProject_feeBean) {
        RePrintProject_feeBean = rePrintProject_feeBean;
    }

    public double getRePrintTicketMoney() {
        return RePrintTicketMoney;
    }

    public void setRePrintTicketMoney(double rePrintTicketMoney) {
        RePrintTicketMoney = rePrintTicketMoney;
    }

    /**
     * 补打商品信息
     * 补打餐饮信息
     */
    public static class RePrintGoodBean {
        //补打类型
        public String RePrintType;
        //商品详情
        public ArrayList<DetailBean.DtBean> list;
        //商品退货
        public ArrayList<ReturnBean> returnBeenlist;
        //支付总额
        public double money;
        //支付订单号
        public String detailID;
        //退货提交的单号
        public String ReDetailID;
        //支付方式
        public String payType_str;
        //支付卡号
        public String cardNO;
        //支付卡余额
        public double cardMoney;

        public ArrayList<ReturnBean> getReturnBeenlist() {
            return returnBeenlist;
        }

        public void setReturnBeenlist(ArrayList<ReturnBean> returnBeenlist) {
            this.returnBeenlist = returnBeenlist;
        }

        public String getRePrintType() {
            return RePrintType;
        }

        public void setRePrintType(String rePrintType) {
            RePrintType = rePrintType;
        }

        public ArrayList<DetailBean.DtBean> getList() {
            return list;
        }

        public void setList(ArrayList<DetailBean.DtBean> list) {
            this.list = list;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getDetailID() {
            return detailID;
        }

        public void setDetailID(String detailID) {
            this.detailID = detailID;
        }

        public String getReDetailID() {
            return ReDetailID;
        }

        public void setReDetailID(String reDetailID) {
            ReDetailID = reDetailID;
        }

        public String getPayType_str() {
            return payType_str;
        }

        public void setPayType_str(String payType_str) {
            this.payType_str = payType_str;
        }

        public String getCardNO() {
            return cardNO;
        }

        public void setCardNO(String cardNO) {
            this.cardNO = cardNO;
        }

        public double getCardMoney() {
            return cardMoney;
        }

        public void setCardMoney(double cardMoney) {
            this.cardMoney = cardMoney;
        }
    }

    //消费支付
    public static class ConsumptionAndPayment {
        //一卡通余额
        public double oneCardMoney;
        //一卡通号
        public String oneCardCode;
        //打印时间
        public String PrintTime;
        //支付类型
        public String PayType;
        //付款金额
        public double PayMoney;
        //商户订单号
        public String MerchantOrder;

        public double getOneCardMoney() {
            return oneCardMoney;
        }

        public void setOneCardMoney(double oneCardMoney) {
            this.oneCardMoney = oneCardMoney;
        }

        public String getOneCardCode() {
            return oneCardCode;
        }

        public void setOneCardCode(String oneCardCode) {
            this.oneCardCode = oneCardCode;
        }

        public String getPrintTime() {
            return PrintTime;
        }

        public void setPrintTime(String printTime) {
            PrintTime = printTime;
        }

        public String getPayType() {
            return PayType;
        }

        public void setPayType(String payType) {
            PayType = payType;
        }

        public double getPayMoney() {
            return PayMoney;
        }

        public void setPayMoney(double payMoney) {
            PayMoney = payMoney;
        }

        public String getMerchantOrder() {
            return MerchantOrder;
        }

        public void setMerchantOrder(String merchantOrder) {
            MerchantOrder = merchantOrder;
        }
    }
}
