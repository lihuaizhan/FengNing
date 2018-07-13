package com.example.tps900.tps900.Bean;


import java.util.List;


/**
 * 项目名称：TPS900_AFK
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/27 15:49
 * 修改人：zxh
 * 修改时间：2017/4/27 15:49
 * 修改备注：
 */

public class Project_feeBean {


    /**
     * cardMoney : 41080.0
     * ticketPrints : [{"sticketnamech":"TVM成人票","dvstartdate":"2017-04-27","dvenddate":"2017-04-27","dvstarttime":"00:00:00","dvendtime":"23:59:59","sbarcode":"170427000000023","sticketannualcouponname":"TVM成人票","sprintdescch":"18周岁以上使用","nprice":"100.00","nprintprice":"100.00?","ntimes":"1","ndealid":"2074","sselldate":"2017-04-27 16:38","sremark":"JJTolJ9W5N7boopeCcV6dw==","npeoplecount":"1","ninparkcount":"1"}]
     * flag : true
     * erro :
     */

    public double cardMoney;
    public boolean flag;
    public String erro;
    public List<TicketPrintsBean> ticketPrints;
    public String PayName;

    public String getPayName() {
        return PayName;
    }

    public void setPayName(String payName) {
        PayName = payName;
    }
    public double getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(double cardMoney) {
        this.cardMoney = cardMoney;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public List<TicketPrintsBean> getTicketPrints() {
        return ticketPrints;
    }

    public void setTicketPrints(List<TicketPrintsBean> ticketPrints) {
        this.ticketPrints = ticketPrints;
    }

    public static class TicketPrintsBean {
        /**
         * sticketnamech : TVM成人票
         * dvstartdate : 2017-04-27
         * dvenddate : 2017-04-27
         * dvstarttime : 00:00:00
         * dvendtime : 23:59:59
         * sbarcode : 170427000000023
         * sticketannualcouponname : TVM成人票
         * sprintdescch : 18周岁以上使用
         * nprice : 100.00
         * nprintprice : 100.00?
         * ntimes : 1
         * ndealid : 2074
         * sselldate : 2017-04-27 16:38
         * sremark : JJTolJ9W5N7boopeCcV6dw==
         * npeoplecount : 1
         * ninparkcount : 1
         */

        public String sticketnamech;
        public String dvstartdate;
        public String dvenddate;
        public String dvstarttime;
        public String dvendtime;
        public String sbarcode;
        public String sticketannualcouponname;
        public String sprintdescch;
        public String nprice;
        public String nprintprice;
        public String ntimes;
        public String ndealid;
        public String sselldate;
        public String sremark;
        public String npeoplecount;
        public String ninparkcount;


        public String getSticketnamech() {
            return sticketnamech;
        }

        public void setSticketnamech(String sticketnamech) {
            this.sticketnamech = sticketnamech;
        }

        public String getDvstartdate() {
            return dvstartdate;
        }

        public void setDvstartdate(String dvstartdate) {
            this.dvstartdate = dvstartdate;
        }

        public String getDvenddate() {
            return dvenddate;
        }

        public void setDvenddate(String dvenddate) {
            this.dvenddate = dvenddate;
        }

        public String getDvstarttime() {
            return dvstarttime;
        }

        public void setDvstarttime(String dvstarttime) {
            this.dvstarttime = dvstarttime;
        }

        public String getDvendtime() {
            return dvendtime;
        }

        public void setDvendtime(String dvendtime) {
            this.dvendtime = dvendtime;
        }

        public String getSbarcode() {
            return sbarcode;
        }

        public void setSbarcode(String sbarcode) {
            this.sbarcode = sbarcode;
        }

        public String getSticketannualcouponname() {
            return sticketannualcouponname;
        }

        public void setSticketannualcouponname(String sticketannualcouponname) {
            this.sticketannualcouponname = sticketannualcouponname;
        }

        public String getSprintdescch() {
            return sprintdescch;
        }

        public void setSprintdescch(String sprintdescch) {
            this.sprintdescch = sprintdescch;
        }

        public String getNprice() {
            return nprice;
        }

        public void setNprice(String nprice) {
            this.nprice = nprice;
        }

        public String getNprintprice() {
            return nprintprice;
        }

        public void setNprintprice(String nprintprice) {
            this.nprintprice = nprintprice;
        }

        public String getNtimes() {
            return ntimes;
        }

        public void setNtimes(String ntimes) {
            this.ntimes = ntimes;
        }

        public String getNdealid() {
            return ndealid;
        }

        public void setNdealid(String ndealid) {
            this.ndealid = ndealid;
        }

        public String getSselldate() {
            return sselldate;
        }

        public void setSselldate(String sselldate) {
            this.sselldate = sselldate;
        }

        public String getSremark() {
            return sremark;
        }

        public void setSremark(String sremark) {
            this.sremark = sremark;
        }

        public String getNpeoplecount() {
            return npeoplecount;
        }

        public void setNpeoplecount(String npeoplecount) {
            this.npeoplecount = npeoplecount;
        }

        public String getNinparkcount() {
            return ninparkcount;
        }

        public void setNinparkcount(String ninparkcount) {
            this.ninparkcount = ninparkcount;
        }
    }
}
