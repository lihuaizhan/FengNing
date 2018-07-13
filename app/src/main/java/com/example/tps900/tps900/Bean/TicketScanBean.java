package com.example.tps900.tps900.Bean;

import java.util.List;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-03-02 14:25
 * 修改人：zxh
 * 修改时间：2018-03-02 14:25
 * 修改备注：
 */

public class TicketScanBean {
    public String ticketName;
    public List<TicketBean> ticketdata;


    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public List<TicketBean> getTicketdata() {
        return ticketdata;
    }

    public void setTicketdata(List<TicketBean> ticketdata) {
        this.ticketdata = ticketdata;
    }

    public static class TicketBean {
        public String ticketPrice;
        public String ticketStartTime;
        public String ticketEndTime;
        public String ticketCode;
        public String shouldPeople;
        public String reallyPeople;
        public int status;

        public String getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(String ticketPrice) {
            this.ticketPrice = ticketPrice;
        }

        public String getTicketStartTime() {
            return ticketStartTime;
        }

        public void setTicketStartTime(String ticketStartTime) {
            this.ticketStartTime = ticketStartTime;
        }

        public String getTicketEndTime() {
            return ticketEndTime;
        }

        public void setTicketEndTime(String ticketEndTime) {
            this.ticketEndTime = ticketEndTime;
        }

        public String getTicketCode() {
            return ticketCode;
        }

        public void setTicketCode(String ticketCode) {
            this.ticketCode = ticketCode;
        }

        public String getShouldPeople() {
            return shouldPeople;
        }

        public void setShouldPeople(String shouldPeople) {
            this.shouldPeople = shouldPeople;
        }

        public String getReallyPeople() {
            return reallyPeople;
        }

        public void setReallyPeople(String reallyPeople) {
            this.reallyPeople = reallyPeople;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TicketScanBean.TicketBean that = (TicketScanBean.TicketBean) o;

            if (!ticketCode.equals(that.ticketCode)) {
                return false;
            }
            return ticketCode.equals(that.ticketCode);

        }

        @Override
        public int hashCode() {
            int result = ticketCode.hashCode();
            result = 31 * result + shouldPeople.hashCode();
            result = 31 * result + reallyPeople.hashCode();
            result = 31 * result + ticketPrice.hashCode();
            result = 31 * result + ticketStartTime.hashCode();
            result = 31 * result + ticketEndTime.hashCode();
            result = 31 * result + status;
            return result;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketScanBean that = (TicketScanBean) o;
        if (ticketdata != that.ticketdata) {
            return false;
        }
        if (!ticketName.equals(that.ticketName)) {
            return false;
        }
        return ticketName.equals(that.ticketName);

    }


    @Override
    public int hashCode() {
        int result = ticketName.hashCode();
        result = 31 * result + ticketdata.hashCode();
        return result;
    }
}
