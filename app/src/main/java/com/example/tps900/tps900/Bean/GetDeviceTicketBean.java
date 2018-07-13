package com.example.tps900.tps900.Bean;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/23 16:02
 * 修改人：zxh
 * 修改时间：2017/4/23 16:02
 * 修改备注：
 */

public class GetDeviceTicketBean implements Serializable {

    /**
     * tickets : [{"nticketid":223,"nformulaid":0,"sticketnamech":"快速通道-一卡通","smnemoniccode":"kstd-ykt","ngeneralprice":100,"nprintprice":100,"npeoplecount":1,"sstarttime":"00:00:00","sendtime":"23:59:59"}]
     * flag : true
     * erro :
     */

    public boolean flag;
    public String erro;
    public ArrayList<TicketsBean> tickets;

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

    public ArrayList<TicketsBean> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<TicketsBean> tickets) {
        this.tickets = tickets;
    }

    public static class TicketsBean implements Serializable {
        /**
         * nticketid : 223
         * nformulaid : 0
         * sticketnamech : 快速通道-一卡通
         * smnemoniccode : kstd-ykt
         * ngeneralprice : 100.0
         * nprintprice : 100.0
         * npeoplecount : 1
         * sstarttime : 00:00:00
         * sendtime : 23:59:59
         */

        public int nticketid;
        public int nformulaid;
        public String sticketnamech;
        public String smnemoniccode;
        public double ngeneralprice;
        public double nprintprice;
        public int npeoplecount;
        public String sstarttime;
        public String sendtime;

        public int getNticketid() {
            return nticketid;
        }

        public void setNticketid(int nticketid) {
            this.nticketid = nticketid;
        }

        public int getNformulaid() {
            return nformulaid;
        }

        public void setNformulaid(int nformulaid) {
            this.nformulaid = nformulaid;
        }

        public String getSticketnamech() {
            return sticketnamech;
        }

        public void setSticketnamech(String sticketnamech) {
            this.sticketnamech = sticketnamech;
        }

        public String getSmnemoniccode() {
            return smnemoniccode;
        }

        public void setSmnemoniccode(String smnemoniccode) {
            this.smnemoniccode = smnemoniccode;
        }

        public double getNgeneralprice() {
            return ngeneralprice;
        }

        public void setNgeneralprice(double ngeneralprice) {
            this.ngeneralprice = ngeneralprice;
        }

        public double getNprintprice() {
            return nprintprice;
        }

        public void setNprintprice(double nprintprice) {
            this.nprintprice = nprintprice;
        }

        public int getNpeoplecount() {
            return npeoplecount;
        }

        public void setNpeoplecount(int npeoplecount) {
            this.npeoplecount = npeoplecount;
        }

        public String getSstarttime() {
            return sstarttime;
        }

        public void setSstarttime(String sstarttime) {
            this.sstarttime = sstarttime;
        }

        public String getSendtime() {
            return sendtime;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }
    }
}
