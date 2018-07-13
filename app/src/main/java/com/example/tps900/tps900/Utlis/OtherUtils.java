package com.example.tps900.tps900.Utlis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.Bean.GetTicket_Bean;
import com.example.tps900.tps900.Bean.PdaBean;
import com.example.tps900.tps900.Bean.Project_feeBean;
import com.example.tps900.tps900.WEBSERVICE_Utils.Constants;
import com.example.tps900.tps900.WEBSERVICE_Utils.Login_Variate;
import com.example.tps900.tps900.sql.PdaDaoUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zxh on 2017/1/12.
 */

public class OtherUtils {
    private static double dis_rate;

    /**
     * double相加
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    // 进行乘法计算
    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    // 进行减法计算
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 关闭键盘
     *
     * @param context
     */
    public static void closeSoftKeyBoard(Activity context) {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 打开键盘
     *
     * @param context
     */
    public static void openSoftKeyBoard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 将读出来的卡号转为10进制
     *
     * @param ATQA
     * @return
     */
    public static String rfidToHand(String ATQA) {
        StringBuffer handCode = new StringBuffer();
        int length = ATQA.length();
        while (length > 0) {
            String substring = ATQA.substring(length - 2, length);
            handCode.append(substring);
            length = length - 2;
        }
        long aa = Long.parseLong(handCode.toString(), 16);
        StringBuffer sb = new StringBuffer(Long.toString(aa));
        for (int j = sb.length(); j < 10; j++) {
            sb.insert(0, "0");
        }
        System.out.println("手牌号码" + sb);
        return sb.toString();
    }


    /**
     * 字节转换方法
     *
     * @param b
     * @param length
     * @return
     */
    public static String byteToHexString(byte[] b, int length) {
        String a = "";
        for (int i = 0; i < length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            a = a + hex;
        }
        return a;
    }

    /**
     * 查询数据库与工具类Constants字段同步
     *
     * @param context
     */
    public static void setConstants(Context context) {
        PdaDaoUtils utils = new PdaDaoUtils();
        //新建查询方法查询数据库,并给Constants类中常量赋值
        List L_info = utils.info_query();
        for (int i = 0; i < L_info.size(); i++) {
            PdaBean bean = (PdaBean) L_info.get(i);
            switch (bean.name) {
//                case "ipAddress":
//                    String ipaddress = bean.value;
//                    if (ipaddress != null) {
//                        Constant.IP_ADDRESS = ipaddress;
//                    }
//                    break;
                case "CHECK_OUT":
                    String CHECK_OUT = bean.value;
                    if (CHECK_OUT != null) {
                        Constant.CHECK_OUT = CHECK_OUT;
                    }
                    break;
                case "deviceName":
                    String deviceName = bean.value;
                    if (deviceName != null) {
                        Constant.devicename = deviceName;
                    }
                    break;
//                case "upipAddress":
//                    String upipAddress = bean.value;
//                    if (upipAddress != null) {
//                        Constant.UPdate_IP_ADDRESS = upipAddress;
//                    }
//                    break;
//                case "S_ipAddress":
//                    String S_ipAddress = bean.value;
//                    if (S_ipAddress != null) {
//                        Constant.S_IP_ADDRESS = S_ipAddress;
//                    }
//                    break;
                case "S_Devicename":
                    String S_Devicename = bean.value;
                    if (S_Devicename != null) {
                        Constant.S_devicename = S_Devicename;
                    }
                    break;
//                case "S_ADDRESS":
//                    String ADDRESS = bean.value;
//                    if (ADDRESS != null) {
//                        Constant.ADDRESS = ADDRESS;
//                    }
//                    break;
//
//                case "Y_IP_ADDRESS":
//                    String y_IP_ADDRESS = bean.value;
//                    if (y_IP_ADDRESS != null) {
//                        Constant.Y_IP_ADDRESS = y_IP_ADDRESS;
//                    }
//                    break;
//                case "Y_NUMBER":
//                    String y_NUMBER = bean.value;
//                    if (y_NUMBER != null) {
//                        Constant.Y_NUMBER = y_NUMBER;
//                    }
//                    break;
//                case "Y_SECRET":
//                    String y_SECRET = bean.value;
//                    if (y_SECRET != null) {
//                        Constant.Y_SECRET = y_SECRET;
//                    }
//                    break;
                case "Y_deviceID":
                    String y_deviceID = bean.value;
                    if (y_deviceID != null) {
                        Constant.Y_deviceID = y_deviceID;
                    }
                    break;
                case "S_TERMINALNAME":
                    String TERMINALNAME = bean.value;
                    if (TERMINALNAME != null) {
                        Constant.TERMINALNAME = TERMINALNAME;
                    }
                    break;
                case "ONECARD_TERMINALNAME":
                    String ONECARD_TERMINALNAME1 = bean.value;
                    if (ONECARD_TERMINALNAME1 != null) {
                        Constant.ONECARD_TERMINALNAME = Integer.valueOf(ONECARD_TERMINALNAME1);
                    }
                    break;
                case "IsCtnCredirCard":
                    String isctncredircard = bean.value;
                    if (isctncredircard != null) {
                        Constant.IsCtnCredirCard = isctncredircard;
                    }
                    break;
                case "F_TERMINALNAME":
                    String F_TERMINALNAME = bean.value;
                    if (F_TERMINALNAME != null) {
                        Constant.Food_TERMINALNAME = F_TERMINALNAME;
                    }
                    break;

                default:
                    break;
            }

        }

    }

    public void getDisplay() {
        // 方法1 Android获得屏幕的宽和高
//        WindowManager windowManager =LoginActivity.this.getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        int screenWidth  = display.getWidth();
//        int screenHeight  = display.getHeight();
//        Log.e("分辨率", "Y="+screenWidth+";X="+screenHeight);
    }

    //金额验证
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    //计算汉字长度
    public static int getStringRealLength(String str) throws Exception {
        String str1 = new String(str.getBytes("GB2312"), "iso-8859-1");
        return str1.length();
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param validateStr 指定的字符串
     * @return 字符串的长度
     */
    public static int getChineseLength(String validateStr) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < validateStr.length(); i++) {
            /* 获取一个字符 */
            String temp = validateStr.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    //价格保留两位小数点０００
    public static Double doubleprice(String price) throws ParseException {
        double countprice = 0.00;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        countprice = Double.valueOf(decimalFormat.format(decimalFormat.parse(price)));//format 返回的是字符串
        return countprice;
    }

    public static String time() {
        String strTime = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        strTime = formatter.format(curDate);// 获取当前时间
        return strTime;
    }

    //字符串转bitmap
    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    //图片转bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {


        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);


        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }

    public static Bitmap getbitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //放大為屏幕的1/2大小
        float screenWidth = 50;     // 屏幕宽（像素，如：480px）
        float screenHeight = 100;        // 屏幕高（像素，如：800p）
        Log.d("screen", screenWidth + "");
        float scaleWidth = screenWidth / 2 / width;
        float scaleHeight = screenWidth / 2 / width;

        // 取得想要缩放的matrix參數
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的圖片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * @author zxh
     * created at 2017/10/9 13:26
     * 方法名: 线下门票售卖 以及补打门票
     * 方法说明:
     */
    public static void Print(Context context, double price, Project_feeBean project_feeBean) {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (project_feeBean.getCardMoney() == 0.0) {
                        for (int i = 0; i < project_feeBean.getTicketPrints().size(); i++) {
                            TpsN900PrintUtils.TpsN900Print_ticket(context, "门票售卖",
                                    "订 单 号 ：" + project_feeBean.getTicketPrints().get(i).getNdealid() +
                                            "\n销售时间：" + project_feeBean.getTicketPrints().get(i).getSselldate() +
                                            "\n支付方式：" + project_feeBean.getPayName() +
                                            "\n门票名称：" + project_feeBean.getTicketPrints().get(i).getSticketnamech() +
                                            "\n门票价格：" + project_feeBean.getTicketPrints().get(i).getNprice() + "元" +
                                            "\n入园开始日期：" + project_feeBean.getTicketPrints().get(i).getDvstartdate() +
                                            "\n                            " + project_feeBean.getTicketPrints().get(i).getDvstarttime() +
                                            "\n入园结束日期：" + project_feeBean.getTicketPrints().get(i).getDvenddate()
                                            + "\n                            " + project_feeBean.getTicketPrints().get(i).getDvendtime() +//npeoplecount
                                            "\n可入园人数：" + project_feeBean.getTicketPrints().get(i).getNpeoplecount() + "人"
                                            + "\n可入景区数：" + project_feeBean.getTicketPrints().get(i).getNinparkcount()
                                            + "\n门票二维码："
                                    , project_feeBean.getTicketPrints().get(i).getSremark(),
                                    "温馨提示：" + project_feeBean.getTicketPrints().get(i).getSprintdescch())
                            ;
                        }
                    } else {
                        double test = OtherUtils.doubleprice(String.valueOf(price));
                        double CuntTPRice = OtherUtils.doubleprice(String.valueOf(project_feeBean.getCardMoney() + test));
                        for (int i = 0; i < project_feeBean.getTicketPrints().size(); i++) {
//                            CuntTPRice = CuntTPRice - (Double.valueOf(project_feeBean.getTicketPrints().get(i).getNprice()) *
//                                    Double.valueOf(project_feeBean.getTicketPrints().get(i).getNpeoplecount()));
                            CuntTPRice = CuntTPRice - Double.valueOf(project_feeBean.getTicketPrints().get(i).getNprice());
                            String format = new DecimalFormat("#0.00").format(CuntTPRice);
                            TpsN900PrintUtils.TpsN900Print_ticket(context, "门票售卖",
                                    //  "景区名称 ：" + Constant.SPARKNAME +
                                    "订 单 号 ：" + project_feeBean.getTicketPrints().get(i).getNdealid() +
                                            "\n销售时间：" + project_feeBean.getTicketPrints().get(i).getSselldate() +
                                            "\n支付方式：" + project_feeBean.getPayName() +
                                            "\n卡内余额：" + format +
                                            "\n门票名称：" + project_feeBean.getTicketPrints().get(i).getSticketnamech() +
                                            "\n门票价格：" + project_feeBean.getTicketPrints().get(i).getNprice() + "元" +
                                            "\n入园开始日期：" + project_feeBean.getTicketPrints().get(i).getDvstartdate() +
                                            "\n                            " + project_feeBean.getTicketPrints().get(i).getDvstarttime() +
                                            "\n入园结束日期：" + project_feeBean.getTicketPrints().get(i).getDvenddate()
                                            + "\n                            " + project_feeBean.getTicketPrints().get(i).getDvendtime() +//npeoplecount
                                            "\n可入园人数：" + project_feeBean.getTicketPrints().get(i).getNpeoplecount() + "人"
                                            + "\n可入景区数：" + project_feeBean.getTicketPrints().get(i).getNinparkcount()
                                            + "\n门票二维码："
                                    , project_feeBean.getTicketPrints().get(i).getSremark(),
                                    "温馨提示：" + project_feeBean.getTicketPrints().get(i).getSprintdescch())
                            ;
                        }
                    }


                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * @author zxh
     * created at 2017/10/9 13:26
     * 方法名: 线上门票售卖
     * 方法说明:
     */
    public static void Ticket_S_Print(Context context, List<GetTicket_Bean.DataBean> PrintList, double Paymoney, double Y_money) {
        try {
            if (Y_money <= 0.0) {
                for (int i = 0; i < PrintList.size(); i++) {
                    TpsN900PrintUtils.TpsN900Print_ticket(context, "门票售卖",
                            "\n---------------------------"
                                    + "\n消费时间 :" + OtherUtils.time()
                                    + "\n收 银 员 :" + Login_Variate.EmployeeName
                                    + "\n订 单 号 :" + PrintList.get(i).getOrderID() +
                                    "\n---------------------------" +
                                    "\n门票名称：" + PrintList.get(i).getProductName() +
                                    "\n门票价格：" + PrintList.get(i).getProductPrice() + "元" +
                                    "\n支付方式：" + PrintList.get(i).getPay_Code() +
                                    "\n付款总额：" + Paymoney + "元" +
                                    //   "\n购票时间：" + time() +
                                    "\n入园开始日期：" + PrintList.get(i).getSTime().substring(0, 10) +
                                    "\n入园结束日期：" + PrintList.get(i).getETime().substring(0, 10) +
                                    "\n限" + PrintList.get(i).getTicketnumber() + "人使用"
                                    + "\n门票二维码："
                            , PrintList.get(i).getEcode(),
                            "温馨提示：" + "本小票只作为入园凭证,不作为报销凭证")
                    ;
                }
            } else {
                dis_rate = Constants.goodsDepositRate * 100;
                for (int i = 0; i < PrintList.size(); i++) {
                    Y_money -= Double.valueOf(PrintList.get(i).getProductPrice()) * Constants.goodsDepositRate;
                    TpsN900PrintUtils.TpsN900Print_ticket(context, "门票售卖",
                            "\n---------------------------"
                                    + "\n消费时间 :" + OtherUtils.time()
                                    + "\n收 银 员 :" + Login_Variate.EmployeeName
                                    + "\n订 单 号 :" + PrintList.get(i).getOrderID() +
                                    "\n---------------------------" +
                                    "\n门票名称：" + PrintList.get(i).getProductName() +
                                    "\n门票价格：" + PrintList.get(i).getProductPrice() + "元" +
                                    "\n支付方式：" + PrintList.get(i).getPay_Code() +
                                    "\n折 扣 率：" + (int) dis_rate + "%" +
                                    "\n折扣金额：" + Double.valueOf(PrintList.get(i).getProductPrice()) * Constants.goodsDepositRate + "元" +
                                    "\n付款总额：" + Paymoney + "元" +
                                    "\n卡内余额：" + Y_money + "元" +
                                    //  "\n购票时间：" + time() +
                                    "\n入园开始日期：" + PrintList.get(i).getSTime().substring(0, 10) +
                                    "\n入园结束日期：" + PrintList.get(i).getETime().substring(0, 10) +
                                    "\n限" + PrintList.get(i).getTicketnumber() + "人使用"
                                    + "\n门票二维码："
                            , PrintList.get(i).getEcode(),
                            "温馨提示：" + "本小票只作为入园凭证,不作为报销凭证")
                    ;
                }
            }

        } catch (Exception e) {

        }
    }

    public static String getTime() {
        //获取当前系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String data = formatter.format(curDate);// 获取当前时间
        return data;
    }

    public static String getUpdataTime() {
        //获取当前系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String data = formatter.format(curDate);// 获取当前时间
        return data;
    }

    /**
     * @param imageUrl
     * @return 中文处理
     */
    public static String transition(String imageUrl) {

        File f = new File(imageUrl);
        if (f.exists()) {
            //正常逻辑代码
        } else {
            //处理中文路径
            imageUrl = Uri.encode(imageUrl);
        }
        imageUrl = imageUrl.replace("%3A", ":");
        imageUrl = imageUrl.replace("%2F", "/");
        return imageUrl;
    }

    /**
     * JSON字符串特殊字符处理，比如：“\A1;1300”
     *
     * @param s
     * @return String
     */
    public static String string2Json(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    //加密方式
    public static String EnCode(String Str) {
        String signature = "";
        try {
            //MD5加密
            String s1 = MD5Encoder.encode(Str);
            String s = new String(s1);
            //将小写字母转成大写字母
            String s3 = s.toUpperCase();

            //Base64加密
            signature = org.kobjects.base64.Base64.encode(s3.getBytes());
            System.out.println("UPPER---->" + signature);
        } catch (Exception e) {
            signature = "-1";
            e.printStackTrace();
        }
        return signature;

    }

    //获取随机数
    public static String getNum() {
        long c = 0;
        long a = System.currentTimeMillis();//获取系统时间
        long b = new Random().nextLong();//获取随机数
        c = a + b;
        char[] d = String.valueOf(Math.abs(c)).toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(d[new Random().nextInt(d.length)]);
        }

        return stringBuilder.toString();
    }

    //金额验证
    public static boolean CheckMoney(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    public static String time_1() {
        String strTime = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        strTime = formatter.format(curDate);// 获取当前时间
        return "\"" + strTime + "T00:00:00" + "\"";
    }

    public static String Json_Time(String time) {
        return time_1();
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 给handler发送错误信息
     *
     * @param caseNum 发送的数字
     */
    public static void sendMessageInfo(Handler handler, int caseNum, String msgInfo) {
        Message msg = Message.obtain();
        msg.what = caseNum;
        msg.obj = msgInfo;
        handler.sendMessage(msg);
    }

    /**
     * 给handler发送错误信息
     *
     * @param caseNum 发送的数字
     */
    public static void sendMessageInfo(Handler handler, int caseNum) {
        Message msg = Message.obtain();
        msg.what = caseNum;
        handler.sendMessage(msg);
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * @author zxh
     * created at 2018-06-07 13:43
     * 方法名: 判断金额是否有误
     * 方法说明:
     */
    public static boolean IsMoneyTrue(double CountMoney) {
        double totalMoney = 0.00;
        for (int i = 0; i < Constant.BEANS.size(); i++) {
            DetailBean.DtBean detailBean = Constant.BEANS.get(i);
            totalMoney += detailBean.getGoodsCount() * detailBean.getPRICE();
        }
        if (CountMoney == totalMoney) {
            return true;
        }
        return false;
    }

    // 将字符串转为时间戳
    public static String getMemberTime() {
        String re_time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(getTime());
            long l = d.getTime();
            String str = String.valueOf(l);
//            re_time = str.substring(0, 10);
            re_time = str;
            Log.e("时间戳------------》", re_time + "---------" + str);
            return re_time;
        } catch (ParseException e) {
        }
        return re_time;
    }
}
