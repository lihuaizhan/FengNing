package com.example.tps900.tps900.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.tps900.tps900.Bean.GetLevelInfo_Bean;
import com.example.tps900.tps900.Bean.GetTicketType_bean;
import com.example.tps900.tps900.Bean.GetTicket_Bean;
import com.example.tps900.tps900.Bean.PdaBean;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.SP;
import com.example.tps900.tps900.Bean.CategoryBean;
import com.example.tps900.tps900.Bean.DetailBean;
import com.godfery.Sqlite.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.tps900.tps900.sql.Sqls.TABLE_CommClass;
import static com.example.tps900.tps900.sql.Sqls.TABLE_ECode;
import static com.example.tps900.tps900.sql.Sqls.TABLE_GzoneComm;
import static com.example.tps900.tps900.sql.Sqls.TABLE_INFO;
import static com.example.tps900.tps900.sql.Sqls.TABLE_LevelInfo;
import static com.example.tps900.tps900.sql.Sqls.TABLE_TICKETS;
import static com.example.tps900.tps900.sql.Sqls.TABLE_TICKETTYPE;
import static com.example.tps900.tps900.sql.Sqls.TABLE_TableType;
import static com.example.tps900.tps900.sql.Sqls.TABLE_WriteOffInfo;
import static com.godfery.Sqlite.DBUtils.rawQuery;


/**
 * Created by zxh on 2016/10/16.
 */

public class PdaDaoUtils {
    //插入表数据
    public void InsertTable(String[] sql) {
        int j = DBUtils.execSQL(sql);
    }

    //查询表数据返回游标
    public static Cursor QueryTable(String sql) {
        Cursor cursor = rawQuery(sql);
        return cursor;
    }

    //删除表
    public static String DeleteTable(String sql) {
        int i = DBUtils.execSQL(sql);
        String t = "";
        if (i <= 0) {
            t = "删除失败！";
            return t;
        } else {
            t = "删除成功";
            return t;
        }
    }

    //关闭数据库
    public static String ColseSql() {
        int i = DBUtils.CloseDB();
        String t = "";
        if (i <= 0) {
            t = "断开数据库失败！";
            return t;
        } else {
            t = "断开数据库成功！";
            return t;
        }
    }

    public static void OpenSql_CreatTable(Context context) {
        if ((boolean) SP.get(context, "CreatSql", false)) {
        } else {
            String[] sql = {TABLE_INFO, TABLE_TableType, TABLE_GzoneComm, TABLE_CommClass, TABLE_TICKETS, TABLE_TICKETTYPE, TABLE_LevelInfo, TABLE_ECode, TABLE_WriteOffInfo};
            Constant.dbUtils = new DBUtils(context, "galasys.db");
            DBUtils.execSQL(sql);
            SP.put(context, "CreatSql", true);
        }


    }


    //设置数据插入数据库
    public int info_insert(Context context) {
        long insert;
        DBUtils.Begin();

        int delete = DBUtils.execSQL("delete  from info");
        if (delete == -1) {
            DBUtils.Rollback();
            return -1;
        }
        ContentValues values = new ContentValues();
        values.clear();
        values.put("_id", 1);
        values.put("name", "CHECK_OUT");
        values.put("value", Constant.CHECK_OUT);
        insert = DBUtils.Insert("info", values);
        if (insert == -1) {
            DBUtils.Rollback();
            return -1;
        }
        values.clear();
        values.put("_id", 2);
        values.put("name", "deviceName");
        values.put("value", Constant.devicename);
        insert = DBUtils.Insert("info", values);
        if (insert == -1) {
            DBUtils.Rollback();
            return -2;
        }
        //设备名称
        values.clear();
        values.put("_id", 3);
        values.put("name", "S_Devicename");
        values.put("value", Constant.S_devicename);
        insert = DBUtils.Insert("info", values);
        if (insert == -1) {
            DBUtils.Rollback();
            return -3;
        }
        //设备名称
        values.clear();
        values.put("_id", 4);
        values.put("name", "S_TERMINALID");
        values.put("value", Constant.TERMINALID);
        insert = DBUtils.Insert("info", values);
        if (insert == -1) {
            DBUtils.Rollback();
            return -4;
        }
        //设备名称
        values.clear();
        values.put("_id", 5);
        values.put("name", "S_TERMINALNAME");
        values.put("value", Constant.TERMINALNAME);
        insert = DBUtils.Insert("info", values);
        if (insert == -1) {
            DBUtils.Rollback();
            return -5;
        }
        //一卡通终端名称
        values.clear();
        values.put("_id", 6);
        values.put("name", "ONECARD_TERMINALNAME");
        values.put("value", Constant.ONECARD_TERMINALNAME);
        insert = DBUtils.Insert("info", values);
        if (insert == -1) {
            DBUtils.Rollback();
            return -6;
        }
        //设备名称
        values.clear();
        values.put("_id", 7);
        values.put("name", "Y_deviceID");
        values.put("value", Constant.Y_deviceID);
        insert = DBUtils.Insert("info", values);
        if (insert == -1) {
            DBUtils.Rollback();
            return -7;
        }
        //设备名称
        values.clear();
        values.put("_id", 8);
        values.put("name", "F_TERMINALNAME");
        values.put("value", Constant.Food_TERMINALNAME);
        insert = DBUtils.Insert("info", values);
        if (insert == -1) {
            DBUtils.Rollback();
            return -8;
        }
        //设备名称
        values.clear();
        values.put("_id",9);
        values.put("name", "IsCtnCredirCard");
        values.put("value", Constant.IsCtnCredirCard);
        insert = DBUtils.Insert("info", values);
        if (insert == -1) {
            DBUtils.Rollback();
            return -9;
        }
        DBUtils.Commit();
        return 1;
    }

    //TODO 设置页面的info表查询
    public List<PdaBean> info_query() {
        Cursor cursor = rawQuery("select * from info");
        //判断cursor是否有数据
        List<PdaBean> list = new ArrayList<PdaBean>();
        if (cursor != null && cursor.getCount() > 0) {
            //遍历结果集获取每一行的数据封装到bean中
            while (cursor.moveToNext()) {
                PdaBean bean = new PdaBean();
                bean.name = cursor.getString(cursor.getColumnIndex("name"));
                bean.value = cursor.getString(cursor.getColumnIndex("value"));
                list.add(bean);
            }
        }
        return list;
    }

    public HashMap<String, String> query_info() {
        HashMap<String, String> hashMap = new HashMap<>();
        String sql = "select name,value from info";
        Cursor cursor = rawQuery(sql);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String value = cursor.getString(cursor.getColumnIndex("value"));
                hashMap.put(name, value);
            }

        }
        return hashMap;

    }


    public ArrayList query_CommClass(String Type) {
        String sql = "select NCLASSID,SCLASSNAME from CommClass Where TYPE = '" + Type + "'";
        ArrayList<CategoryBean.DtBean> names = new ArrayList<>();
        Cursor cursor = rawQuery(sql);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                CategoryBean.DtBean bean = new CategoryBean.DtBean();
                String SCLASSNAME = cursor.getString(cursor.getColumnIndex("SCLASSNAME"));
                String NCLASSID = cursor.getString(cursor.getColumnIndex("NCLASSID"));

                bean.setSCLASSNAME(SCLASSNAME);
                bean.setNCLASSID(Integer.parseInt(NCLASSID));

                names.add(bean);
            }
        }
        return names;
    }

    public static ArrayList query_CommClass_2_XS() {
        ArrayList<GetTicketType_bean.DataBean> list = new ArrayList<GetTicketType_bean.DataBean>();
        Cursor cursor = null;
        try {
            String sql = "select * from TicketType where ProductType='1' ";
            cursor = rawQuery(sql);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    GetTicketType_bean.DataBean bean = new GetTicketType_bean.DataBean();
                    // 类别ID
                    bean.TypeId = cursor.getString(cursor
                            .getColumnIndex("TypeId"));
                    // 类别名称
                    bean.TypeName = cursor.getString(cursor
                            .getColumnIndex("TypeName"));
                    // 产品类型
                    bean.ProductType = cursor.getString(cursor
                            .getColumnIndex("ProductType"));

                    list.add(bean);

                }
                return list;
            }

        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static ArrayList query_TableType() {
        ArrayList<GetTicketType_bean.DataBean> list = new ArrayList<GetTicketType_bean.DataBean>();
        Cursor cursor = null;
        try {
            String sql = "select * from TableType ";
            cursor = rawQuery(sql);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    GetTicketType_bean.DataBean bean = new GetTicketType_bean.DataBean();
                    // 类别ID
                    bean.TypeId = String.valueOf(cursor.getInt(cursor
                            .getColumnIndex("DTABLETYPE")));
                    // 类别名称
                    bean.TypeName = cursor.getString(cursor
                            .getColumnIndex("DTABLETYPENAME"));
                    list.add(bean);

                }
                return list;
            }

        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public ArrayList query_GzoneComm_XS(String sql) {
        ArrayList<DetailBean.DtBean> beanContainer = new ArrayList<>();
        Cursor cursor = rawQuery(sql);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                DetailBean.DtBean bean = new DetailBean.DtBean();
                String ncommid = cursor.getString(cursor.getColumnIndex("NCOMMID"));
                int npcommid = cursor.getInt(cursor.getColumnIndex("NPCOMMID"));
                int pnumber = cursor.getInt(cursor.getColumnIndex("PNUMBER"));
                String vcommcoding = cursor.getString(cursor.getColumnIndex("VCOMMCODING"));

                String name = cursor.getString(cursor.getColumnIndex("ProductName"));
                double price = cursor.getDouble(cursor.getColumnIndex("ProductPrice"));


                bean.setVCOMMYNAME(name);
                bean.setPRICE(price);
                bean.setNCOMMID(ncommid);
                bean.setNPCOMMID(npcommid);
                bean.setPNUMBER(pnumber);
                bean.setVCOMMCODING(vcommcoding);
                beanContainer.add(bean);

            }

        }
        cursor.close();
        return beanContainer;
    }

    public static ArrayList query_GzoneComm_2_XS(String typeID, String ProductId) {
        // 调用时先打开数据库
        ArrayList<GetTicket_Bean.DataBean> list = new ArrayList<GetTicket_Bean.DataBean>();
        Cursor cursor = null;
        try {
//            String   sql = "select ProductId,ProductName, ProductPrice, ProductCode, ProductType, PrintName,Remark, QuoteId, TypeId " +
//                    "from  Tickets where TypeId like '" + typeID + "' and ProductId like '" + ProductId + "'";
            String sql = "select * from Tickets where TypeId = " + "'" + typeID + "'";
            cursor = rawQuery(sql);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    GetTicket_Bean.DataBean bean = new GetTicket_Bean.DataBean();
                    bean.ProductId = cursor.getString(cursor
                            .getColumnIndex("ProductId"));
                    bean.ProductName = cursor.getString(cursor
                            .getColumnIndex("ProductName"));
                    bean.ProductPrice = cursor.getString(cursor
                            .getColumnIndex("ProductPrice"));
                    bean.ProductCode = cursor.getString(cursor
                            .getColumnIndex("ProductCode"));
                    bean.ProductType = cursor.getString(cursor
                            .getColumnIndex("ProductType"));
                    bean.PrintName = cursor.getString(cursor
                            .getColumnIndex("PrintName"));
                    bean.Remark = cursor.getString(cursor
                            .getColumnIndex("Remark"));
                    bean.QuoteId = cursor.getString(cursor
                            .getColumnIndex("QuoteId"));
                    bean.TypeId = cursor.getString(cursor
                            .getColumnIndex("TypeId"));
                    list.add(bean);

                }

                return list;
            }

        } catch (Exception e) {

            return null;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public ArrayList query_GzoneComm_EditText(String sql, String[] selectionArgs) {
        ArrayList<DetailBean.DtBean> beanContainer = new ArrayList<>();
        Cursor cursor = rawQuery(sql, selectionArgs);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                DetailBean.DtBean bean = new DetailBean.DtBean();

                String ncommid = cursor.getString(cursor.getColumnIndex("NCOMMID"));
                int npcommid = cursor.getInt(cursor.getColumnIndex("NPCOMMID"));
                int pnumber = cursor.getInt(cursor.getColumnIndex("PNUMBER"));
                String vcommcoding = cursor.getString(cursor.getColumnIndex("VCOMMCODING"));

                String name = cursor.getString(cursor.getColumnIndex("VCOMMYNAME"));
                double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));
                int classID = cursor.getInt(cursor.getColumnIndex("classID"));

                bean.setVCOMMYNAME(name);
                bean.setPRICE(price);
                bean.setNCOMMID(ncommid);
                bean.setNPCOMMID(npcommid);
                bean.setPNUMBER(pnumber);
                bean.setVCOMMCODING(vcommcoding);
                bean.setClassId(classID);
                beanContainer.add(bean);
            }

        }

        cursor.close();

        return beanContainer;
    }

    //获取卡级信息
    public static ArrayList<GetLevelInfo_Bean.DataBean> getQuery_LevelInfo(String sql) {
        // 调用时先打开数据库
        ArrayList<GetLevelInfo_Bean.DataBean> list = new ArrayList<GetLevelInfo_Bean.DataBean>();
        Cursor cursor = rawQuery(sql, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    GetLevelInfo_Bean.DataBean bean = new GetLevelInfo_Bean.DataBean();
                    bean.LevelId = cursor.getString(cursor
                            .getColumnIndex("LevelId"));
                    bean.LevelName = cursor.getString(cursor
                            .getColumnIndex("LevelName"));
                    bean.IsStored = cursor.getString(cursor
                            .getColumnIndex("IsStored"));
                    bean.IsVipMark = cursor.getString(cursor
                            .getColumnIndex("IsVipMark"));
                    bean.IsAdJust = cursor.getString(cursor
                            .getColumnIndex("IsAdjust"));
                    bean.DepositAmount = cursor.getString(cursor
                            .getColumnIndex("DepositAmount"));
                    bean.DepositItid = cursor.getString(cursor
                            .getColumnIndex("DepositItid"));
                    bean.RentAmount = cursor.getString(cursor
                            .getColumnIndex("RentAmount"));
                    bean.RentItid = cursor.getString(cursor
                            .getColumnIndex("RentItid"));
                    bean.ReissueAmount = cursor.getString(cursor
                            .getColumnIndex("ReissueAmount"));
                    bean.INTEGRALRATE = cursor.getDouble(cursor
                            .getColumnIndex("INTEGRALRATE"));
                    bean.DEPOSITRATE = cursor.getDouble(cursor
                            .getColumnIndex("DEPOSITRATE"));
                    list.add(bean);

                }
            }

        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (list != null) {
            return list;
        } else {
            return null;
        }
    }

    public ArrayList query_GzoneComm(String sql) {
        ArrayList<DetailBean.DtBean> beanContainer = new ArrayList<>();
        Cursor cursor = rawQuery(sql);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                DetailBean.DtBean bean = new DetailBean.DtBean();
                String ncommid = cursor.getString(cursor.getColumnIndex("NCOMMID"));
                int npcommid = cursor.getInt(cursor.getColumnIndex("NPCOMMID"));
                int pnumber = cursor.getInt(cursor.getColumnIndex("PNUMBER"));
                String vcommcoding = cursor.getString(cursor.getColumnIndex("VCOMMCODING"));

                String name = cursor.getString(cursor.getColumnIndex("VCOMMYNAME"));
                double price = cursor.getDouble(cursor.getColumnIndex("PRICE"));


                bean.setVCOMMYNAME(name);
                bean.setPRICE(price);
                bean.setNCOMMID(ncommid);
                bean.setNPCOMMID(npcommid);
                bean.setPNUMBER(pnumber);
                bean.setVCOMMCODING(vcommcoding);
                beanContainer.add(bean);
            }

        }
        cursor.close();
        return beanContainer;
    }

    /**
     * 判断商品是否有库存
     * @param sql
     * @return
     */
    public static boolean query_GzoneComm_Stock(String sql) {
        Cursor cursor = rawQuery(sql);
        boolean isStock = false;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int NCOMMISINVENTORY = cursor.getInt(cursor.getColumnIndex("NCOMMISINVENTORY"));
                if (NCOMMISINVENTORY != 0) {
                    isStock = true;
                }
            }

        }
        cursor.close();
        return isStock;
    }
}
