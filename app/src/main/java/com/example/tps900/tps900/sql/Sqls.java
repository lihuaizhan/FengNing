package com.example.tps900.tps900.sql;

import android.database.Cursor;

import com.example.tps900.tps900.Bean.ScanEcodeBean;
import com.example.tps900.tps900.Bean.TicketScanBean;
import com.godfery.Sqlite.DBUtils;

import java.util.ArrayList;
import java.util.List;

import static com.godfery.Sqlite.DBUtils.rawQuery;

/**
 * Created by wo on 2017/4/25.
 */

public class Sqls {

    public static String TABLE_INFO = "create table info" + "("
            + "_id                  INTEGER                        not null,"
            + "name                 VARCHAR(30)                    not null,"
            + "value                VARCHAR(30)                    not null,"
            + "primary key (_id)" + ")";
    /**
     * type类型为1是商品   2是餐饮
     */
    public static final String TABLE_GzoneComm = "create table GzoneComm ("
            + "TID              	   varchar(40)                    not null,"
            + "NCOMMID              int                            not null,"
            + "classID              int                            not null,"
            + "VCOMMBARCODE         varchar(20)                    not null,"
            + "VCOMMCODING          varchar(30)                    not null,"
            + "VCOMMYNAME           varchar(60)                    not null,"
            + "PRICE                DECIMAL(16,2)                  not null,"
            + "NPCOMMID             varchar(10)                    not null,"
            + "PNUMBER              int                            not null,"
            + "SIMPLIFY             varchar(20)                    not null,"
            + "ATTENTION             varchar(20)                    not null,"
            + "TYPE                 int                    not null,"
            + "NCOMMISINVENTORY       int                    ,"//是否管理库存
            + "primary key (TID,TYPE)"
            + ")";
    /**
     * type类型为1是商品   2是餐饮
     */
    public static final String TABLE_CommClass = "create table CommClass ("
            + "NCLASSID             int                            not null,"
            + "CODE                 varchar(20)                    not null,"
            + "SCLASSNAME           varchar(40)                    not null,"
            + "TYPE                 int                    not null,"
            + "primary key (NCLASSID,TYPE)"
            + ")";



	/*
     * 1.2 产品信息表
	 *
	 * 产品ID ProductId , 产品名称 ProductName ,产品价格 ProductPrice , 产品编码 ProductCode
	 * 产品类型ProductType , 打印名称 PrintName ,打印价格 PrintPrice , 备注 Remark 报价单ID
	 * QuoteId ,产品类别TypeId
	 *
	 */

    public static final String TABLE_TICKETS = "create table Tickets" + "("
            + "ProductId            varchar(20)                    not null,"
            + "ProductName          varchar(50)                    not null,"
            + "ProductPrice         DECIMAL(10,2)                  not null,"
            + "ProductCode          varchar(20)                            ,"
            + "ProductType          varchar(3)                     not null,"
            + "PrintName            varchar(50)                            ,"
            + "PrintPrice           DECIMAL(10,2)                  not null,"
            + "Remark               varchar(100),"
            + "QuoteId              varchar(50)                    not null,"
            + "TypeId               varchar(10)                    not null,"
            + "primary key (ProductId)" + ")";

/*
     * 2.1 产品类别表,即门票类别表
	 *
	 * 类别ID TypeId , 类别名称 TypeName , 产品类型 ProductType
	 *
	 */

    public static final String TABLE_TICKETTYPE = "create table TicketType"
            + "("
            + "TypeId               varchar(10)                    not null,"
            + "TypeName             varchar(20)                    not null,"
            + "ProductType          varchar(3)                     not null,"
            + "primary key (TypeId)" + ")";

    /**
     * type类型为1是包间桌   2是散客桌
     */
    public static final String TABLE_TableType = "create table TableType ("
            + "DTABLETYPE             int                            not null,"
            + "DTABLETYPENAME         varchar(20)                    not null,"
            + "primary key (DTABLETYPE)"
            + ")";

    /**
     * 桌台信息
     * "NID":46,
     * "DDESKNUMBER":"1",
     * "DESKNAME":"一号桌",
     * "DPRICE":0,
     * "DNUMBER":54,
     * "DREMARKS":"",
     * "DNGZONEID":2758,
     * "DCREATTIME":"2016-11-10T15:30:08",
     * "UPDATETIME":"2016-11-10T15:31:05",
     * "ISENABLE":0,
     * "DTABLETYPE":2
     */
    public static final String TABLE_TableInfo = "create table TableInfo ("
            + "NID                     int                           not null,"
            + "DDESKNUMBER             varchar(20)                       not null,"
            + "DESKNAME                varchar(20)                       not null,"
            + "DPRICE                  varchar(20)                       not null,"
            + "DNUMBER                 varchar(20)                       not null,"
            + "DREMARKS                varchar(20)                       not null,"
            + "DNGZONEID               int                               not null,"
            + "DCREATTIME              varchar(20)                       not null,"
            + "UPDATETIME              varchar(20)                       not null,"
            + "ISENABLE                varchar(20)                       not null,"
            + "DTABLETYPE              int                               not null,"
            + "primary key (DTABLETYPE)"
            + ")";
    /**
     * 11获取卡级信息表
     * LevelId	卡级ID	string（20）	是
     * LevelName	卡级名称	String（50）	否
     * IsStored	是否允许储值	string（1）	是
     * IsVipMark	是否允许积分	string(1)	是
     * IsAdjust	是否允许调整	string(1)	是
     * DepositAmount	押金金额	decimal	否
     * DepositItid	押金商品Id	string(20)	否
     * RentAmount	租金金额	decimal	否
     * RentItid	租金商品	string(20)	否
     * ReissueAmount	补办金额	decimal	否
     * Reissueitid	补办商品	string（20）	否
     * INTEGRALRATE 积分率
     * DEPOSITRATE  折扣率
     */
    public static final String TABLE_LevelInfo = "create table LevelInfo" + "("
            + "LevelId                varchar(20)                   not null ,"
            + "INTEGRALRATE           DECIMAL(10,2)                          ,"
            + "DEPOSITRATE            DECIMAL(10,2)                          ,"
            + "LevelName              varchar(20)                            ,"
            + "IsStored               varchar(1)                             ,"
            + "IsVipMark              varchar(1)                             ,"
            + "IsAdjust               varchar(1)                             ,"
            + "DepositAmount          DECIMAL(10,2)                          ,"
            + "DepositItid            varchar(20)                            ,"
            + "RentAmount             DECIMAL(10,2)                          ,"
            + "RentItid               varchar(20)                            ,"
            + "ReissueAmount          DECIMAL(10,2)                          ,"
            + "Reissueitid            varchar(20)                            ,"
            + "primary key (LevelId)" + ")";
    //串码表
    public static final String TABLE_ECode = "create table Ecode" + "("
            + "_id                  INTEGER                        not null,"
            + "TicketEcode                 VARCHAR(30)                    not null,"
            + "primary key (_id)" + ")";
    //门票核销状态0未核销  1  已核销
    public static final String TABLE_WriteOffInfo = "create table WriteOffInfo" + "("
            + "ProductName          varchar(50)                     not null,"
            + "ProductPrice         varchar(50)                     not null,"
            + "TicketEcode          varchar(50)                     not null,"
            + "StartTime            varchar(50)                     not null,"
            + "EndTime              varchar(50)                     not null,"
            + "ShouldPeople          int                            not null ,"
            + "ReallyPeople          int                            not null,"
            + "Ticketstatus          int                            not null,"
            + "primary key (TicketEcode)" + ")";

    //添加二维码
    public static void InsertEcodeTablet(String ecode) {
        String sql = "insert into Ecode(TicketEcode) values" +
                "(\"" + ecode + "\")";
        DBUtils.execSQL(sql);
    }

    //添加门票核销
    public static void InsertWriteOffInfo(List<TicketScanBean> scanBeanList) {
        for (int i = 0; i < scanBeanList.size(); i++) {
            TicketScanBean ticketscanBean = scanBeanList.get(i);
            for (int j = 0; j < ticketscanBean.getTicketdata().size(); j++) {
                TicketScanBean.TicketBean ticketname = ticketscanBean.getTicketdata().get(j);
                String sql = "insert into WriteOffInfo( ProductName,ProductPrice,TicketEcode,StartTime,EndTime,ShouldPeople,ReallyPeople,Ticketstatus) " +
                        "values" +
                        "(" +
                        "\"" + ticketscanBean.getTicketName() + "\"," +
                        "\"" + ticketname.getTicketPrice() + "\"," +
                        "\"" + ticketname.getTicketCode() + "\"," +
                        "\"" + ticketname.getTicketStartTime() + "\"," +
                        "\"" + ticketname.getTicketEndTime() + "\"," +
                        "\"" + ticketname.getShouldPeople() + "\"," +
                        "\"" + ticketname.getReallyPeople() + "\"," +
                        "\"" + ticketname.getStatus() + "\")";
                DBUtils.execSQL(sql);
            }

        }


    }

    /**
     * 添加餐桌类型
     */
    public static void InsertTableType() {
        String dltTableType = "delete from TableType";
        DBUtils.execSQL(dltTableType);
        String sql = "insert into TableType(DTABLETYPE,DTABLETYPENAME) values" +
                "(\"" + 1 + "\",\"" + "包间桌" + "\")";
        String sql2 = "insert into TableType(DTABLETYPE,DTABLETYPENAME) values" +
                "(\"" + 2 + "\",\"" + "散客桌" + "\")";
        DBUtils.execSQL(sql);
        DBUtils.execSQL(sql2);
    }

    //查询门票二维码
    public static List<ScanEcodeBean> queryTicketEcode() {
        Cursor cursor = rawQuery("select * from Ecode");
        //判断cursor是否有数据
        List<ScanEcodeBean> list = new ArrayList<ScanEcodeBean>();
        if (cursor != null && cursor.getCount() > 0) {
            //遍历结果集获取每一行的数据封装到bean中
            while (cursor.moveToNext()) {
                ScanEcodeBean bean = new ScanEcodeBean();
                bean.Ecode = cursor.getString(cursor.getColumnIndex("TicketEcode"));
                list.add(bean);
            }
        }
        return list;
    }

    //根据串码查询记录
    public static String queryTicketEcode(String ecode) {
        Cursor cursor = rawQuery("select * from Ecode where TicketEcode = '" + ecode + "'");
        //判断cursor是否有数据
        String Ecode = "";
        if (cursor != null && cursor.getCount() > 0) {
            //遍历结果集获取每一行的数据封装到bean中
            while (cursor.moveToNext()) {
                TicketScanBean.TicketBean bean = new TicketScanBean.TicketBean();
                Ecode = bean.ticketCode = cursor.getString(cursor.getColumnIndex("TicketEcode"));
            }
        }
        return Ecode;
    }

    //查询门票核销状态
    public static List<TicketScanBean> queryWriteOffInfo(String sql) {
        Cursor cursor = rawQuery(sql);
        //ProductId,ProductName,TicketEcode,ShouldPeople,ReallyPeople,Ticketstatus
        //判断cursor是否有数据
        List<TicketScanBean> list = new ArrayList<TicketScanBean>();
        if (cursor != null && cursor.getCount() > 0) {
            //遍历结果集获取每一行的数据封装到bean中
            while (cursor.moveToNext()) {
                TicketScanBean bean = new TicketScanBean();
                bean.ticketName = cursor.getString(cursor.getColumnIndex("ProductName"));
                TicketScanBean.TicketBean ticketBean = new TicketScanBean.TicketBean();
                ticketBean.ticketPrice = cursor.getString(cursor.getColumnIndex("ProductPrice"));
                ticketBean.ticketStartTime = cursor.getString(cursor.getColumnIndex("StartTime"));
                ticketBean.ticketEndTime = cursor.getString(cursor.getColumnIndex("EndTime"));
                ticketBean.ticketCode = cursor.getString(cursor.getColumnIndex("TicketEcode"));
                ticketBean.shouldPeople = cursor.getString(cursor.getColumnIndex("ShouldPeople"));
                ticketBean.reallyPeople = cursor.getString(cursor.getColumnIndex("ReallyPeople"));
                ticketBean.status = cursor.getInt(cursor.getColumnIndex("Ticketstatus"));
                List<TicketScanBean.TicketBean> ticketBeanList = new ArrayList<>();
                ticketBeanList.add(ticketBean);
                bean.setTicketdata(ticketBeanList);
                list.add(bean);
            }
        }
        return list;
    }

    //查询门票核销状态
    public static String queryWriteOffInfoStatus(String Ecode) {
        Cursor cursor = rawQuery("select * from WriteOffInfo");
        //ProductId,ProductName,TicketEcode,ShouldPeople,ReallyPeople,Ticketstatus
        //判断cursor是否有数据
        String ecode = "";
        if (cursor != null && cursor.getCount() > 0) {
            //遍历结果集获取每一行的数据封装到bean中
            while (cursor.moveToNext()) {
                ecode = cursor.getString(cursor.getColumnIndex("TicketEcode"));
            }
        }
        return ecode;
    }

    //根据串码删除某一行
    public static void deleteWriteOffInfo(String Ecode) {
        String deleteSql = "delete from WriteOffInfo where TicketEcode =" + "'" + Ecode + "'";
        DBUtils.execSQL(deleteSql);
    }

    //根据串码删除某一行
    public static void deleteEcode(String Ecode) {
        String deleteSql = "delete from Ecode where TicketEcode =" + "'" + Ecode + "'";
        DBUtils.execSQL(deleteSql);
    }

    /**
     * 修改核销日志表中的核销状态和实际人数
     *
     * @param name  修改状态/修改人数
     * @param ecode 二维码
     * @param value 值
     */
    public static void updataWriteOffInfo(String name, String ecode, String value) {
        if (name.equals("修改状态")) {
            String sql = "UPDATE WriteOffInfo SET Ticketstatus = '" + 1 + "' WHERE TicketEcode = '" + ecode + "' ";
            DBUtils.execSQL(sql);
        } else if (name.equals("修改人数")) {
            String sql = "UPDATE WriteOffInfo SET ReallyPeople = '" + value + "' WHERE TicketEcode = '" + ecode + "' ";
            DBUtils.execSQL(sql);
        }

    }

}
