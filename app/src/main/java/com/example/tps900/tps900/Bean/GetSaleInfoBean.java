package com.example.tps900.tps900.Bean;

import java.util.List;


/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/23 20:34
 * 修改人：zxh
 * 修改时间：2017/4/23 20:34
 * 修改备注：
 */

public class GetSaleInfoBean {

    /**
     * ticketPrints : [{"sticketnamech":"快速通道-一卡通","dvstartdate":"2017-04-23","dvenddate":"2017-04-23","dvstarttime":"00:00:00","dvendtime":"23:59:59","sbarcode":"170423000000001","sticketannualcouponname":"快速通道-一卡通","sprintdescch":"当天有效","nprice":"100.00","nprintprice":"100.00?","ntimes":"1","ndealid":"1897","sselldate":"2017-04-23 20:33","sremark":"eZTSfgVLbCspG9qQ4qvJBA==","npeoplecount":"1","ninparkcount":"1"}]
     * flag : true
     * erro : null
     */

    public boolean flag;
    public Object erro;
    public List<TicketPrintsBean> ticketPrints;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getErro() {
        return erro;
    }

    public void setErro(Object erro) {
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
         * sticketnamech : 快速通道-一卡通
         * dvstartdate : 2017-04-23
         * dvenddate : 2017-04-23
         * dvstarttime : 00:00:00
         * dvendtime : 23:59:59
         * sbarcode : 170423000000001
         * sticketannualcouponname : 快速通道-一卡通
         * sprintdescch : 当天有效
         * nprice : 100.00
         * nprintprice : 100.00?
         * ntimes : 1
         * ndealid : 1897
         * sselldate : 2017-04-23 20:33
         * sremark : eZTSfgVLbCspG9qQ4qvJBA==
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
