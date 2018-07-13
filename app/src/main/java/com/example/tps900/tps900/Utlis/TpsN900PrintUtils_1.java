package com.example.tps900.tps900.Utlis;

/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/23 20:59
 * 修改人：zxh
 * 修改时间：2017/4/23 20:59
 * 修改备注：
 */

public class TpsN900PrintUtils_1 {
//    /**
//     * @author zxh
//     * created at 2017/6/27 13:29
//     * 方法名: TpsN900OneCardPrint
//     * 方法说明: 商品售卖中的一卡通打印小票清单
//     */
//    public static void TpsN900OneCardPrint(Context context, ArrayList<DetailBean.DtBean> list, double money, String orderNo, String payType, String cardNo, double cardMoney) throws Exception {
//        int totalCount = 0;
//        for (int i = 0; i < list.size(); i++) {
//            DetailBean.DtBean detailBean = list.get(i);
//            totalCount += detailBean.getGoodsCount();
//        }
//        UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//        try {
//
//            mUsbThermalPrinter.start(0);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("\n" + "商品清单");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setGray(5);
//            mUsbThermalPrinter.addString("消费时间:  " + TimeUtils.time());
//            mUsbThermalPrinter.addString("收银   员:  " + Constant.USERNAME);
//            mUsbThermalPrinter.addString("订单编号:  " + orderNo);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
////            mUsbThermalPrinter.addString("名称/" + "条码" + "   单价   数量   小计");
//            mUsbThermalPrinter.addStringOffset(0, "名称/条码");
//            mUsbThermalPrinter.addStringOffset(160, "数量");
//            mUsbThermalPrinter.addStringOffset(240, "价格");
//            mUsbThermalPrinter.addStringOffset(320, "小计");
//            mUsbThermalPrinter.endLine();
//            LogUtil.i("TpsN900PrintUtils", "list的大小--->：" + list.size());
//            for (int i = 0; i < list.size(); i++) {
//                DetailBean.DtBean bean = list.get(i);
////                mUsbThermalPrinter.addString(setStringLength(bean.getVCOMMYNAME(), 16));
////                mUsbThermalPrinter.addString(
////                        setStringLength(bean.getNCOMMID(), 6)
////                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1))
////                                + "     " + String.valueOf(bean.getGoodsCount())
////                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount()))
////                );
//                mUsbThermalPrinter.addStringOffset(0, bean.getVCOMMYNAME());
//                mUsbThermalPrinter.endLine();
//                mUsbThermalPrinter.addStringOffset(0, bean.getNCOMMID());
//                mUsbThermalPrinter.addStringOffset(160, String.valueOf(bean.getGoodsCount()));
//                mUsbThermalPrinter.addStringOffset(240, new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1)));
//                mUsbThermalPrinter.addStringOffset(320, new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount())));
//                mUsbThermalPrinter.endLine();
//            }
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居中
//            mUsbThermalPrinter.addString("合计：" + new DecimalFormat("#0.00").format(money) + "元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("支付类型:" + payType);
//            mUsbThermalPrinter.addString("购买数量:" + String.valueOf(totalCount));
//            mUsbThermalPrinter.addString("付款金额:" + new DecimalFormat("#0.00").format(money) + " 元");
//            mUsbThermalPrinter.addString("支付卡号:" + cardNo);
//            mUsbThermalPrinter.addString("卡内余额:" + new DecimalFormat("#0.00").format(cardMoney));
//            mUsbThermalPrinter.addString("-------------------------");
//            Bitmap bitmap = CreateCode(orderNo, BarcodeFormat.QR_CODE, 160, 160);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setGray(3);
//            mUsbThermalPrinter.printLogo(bitmap, true);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("请妥善保存本小票");
//            mUsbThermalPrinter.printString();
//            mUsbThermalPrinter.walkPaper(10);
//
//        } catch (Exception e) {
//
//        } finally {
//            mUsbThermalPrinter.stop();
//        }
//    }
//
//    /**
//     * @author zxh
//     * created at 2017/6/27 13:29
//     * 方法名: TpsN900Print
//     * 方法说明: 商品售卖中的打印小票清单
//     */
//    public static void TpsN900Print(Context context, ArrayList<DetailBean.DtBean> list, double money, String orderNo, String payType) throws Exception {
//        int totalCount = 0;
//        for (int i = 0; i < list.size(); i++) {
//            DetailBean.DtBean detailBean = list.get(i);
//            totalCount += detailBean.getGoodsCount();
//        }
//        UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//        try {
//            mUsbThermalPrinter.start(0);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("\n" + "商品清单");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(5);
//            mUsbThermalPrinter.addString("消费时间:  " + TimeUtils.time());
//            mUsbThermalPrinter.addString("收银   员:  " + Constant.USERNAME);
//            mUsbThermalPrinter.addString("订单编号:  " + orderNo);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(5);
////            mUsbThermalPrinter.addString("名称/" + "条码" + "   单价   数量   小计");
//            mUsbThermalPrinter.addStringOffset(0, "名称/条码");
//            mUsbThermalPrinter.addStringOffset(160, "数量");
//            mUsbThermalPrinter.addStringOffset(240, "价格");
//            mUsbThermalPrinter.addStringOffset(320, "小计");
//            mUsbThermalPrinter.endLine();
//            LogUtil.i("TpsN900PrintUtils", "list的大小--->：" + list.size());
//            for (int i = 0; i < list.size(); i++) {
//                DetailBean.DtBean bean = list.get(i);
////                mUsbThermalPrinter.addString(setStringLength(bean.getVCOMMYNAME(), 16));
////                mUsbThermalPrinter.addString(
////                        setStringLength(bean.getNCOMMID(), 6)
////                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1))
////                                + "     " + String.valueOf(bean.getGoodsCount())
////                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount()))
////                );
//                mUsbThermalPrinter.addStringOffset(0, bean.getVCOMMYNAME());
//                mUsbThermalPrinter.endLine();
//                mUsbThermalPrinter.addStringOffset(0, bean.getNCOMMID());
//                mUsbThermalPrinter.addStringOffset(160, String.valueOf(bean.getGoodsCount()));
//                mUsbThermalPrinter.addStringOffset(240, new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1)));
//                mUsbThermalPrinter.addStringOffset(320, new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount())));
//                mUsbThermalPrinter.endLine();
//            }
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.addString("合计：" + new DecimalFormat("#0.00").format(money) + "元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("支付类型:" + payType);
//            mUsbThermalPrinter.addString("购买数量:" + String.valueOf(totalCount));
//            mUsbThermalPrinter.addString("付款金额:" + new DecimalFormat("#0.00").format(money) + " 元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.printString();
//
//            Bitmap bitmap = CreateCode(orderNo, BarcodeFormat.QR_CODE, 160, 160);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.printLogo(bitmap, false);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("请妥善保存本小票");
//            mUsbThermalPrinter.printString();
//            mUsbThermalPrinter.walkPaper(10);
//        } catch (Exception e) {
//
//        } finally {
//            mUsbThermalPrinter.stop();
//        }
//    }
//
//    public static void TpsN900Print_Food(Context context, ArrayList<DetailBean.DtBean> list, double money, String orderNo, String payType, String tableNo, String tableName) throws Exception {
//        int totalCount = 0;
//        for (int i = 0; i < list.size(); i++) {
//            DetailBean.DtBean detailBean = list.get(i);
//            totalCount += detailBean.getGoodsCount();
//        }
//        UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//        try {
//            //都江堰
//            mUsbThermalPrinter.start(0);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("\n" + "餐饮清单");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(5);
//            mUsbThermalPrinter.addString("消费时间:  " + TimeUtils.time());
//            mUsbThermalPrinter.addString("收银   员:  " + Constant.USERNAME);
//            mUsbThermalPrinter.addString("订单编号:  " + orderNo);
//            mUsbThermalPrinter.addString("桌台编号:  " + tableNo);
//            mUsbThermalPrinter.addString("桌台名称:  " + tableName);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(5);
//            mUsbThermalPrinter.addString("名称               条码 ");
//            mUsbThermalPrinter.addString("数量           价格         小计");
//            LogUtil.i("TpsN900PrintUtils", "list的大小--->：" + list.size());
//
//            for (int i = 0; i < list.size(); i++) {
//                DetailBean.DtBean bean = list.get(i);
//                mUsbThermalPrinter.addString(
//                        setStringLength(bean.getVCOMMYNAME(), 16)
//                                + setStringLength(String.valueOf(bean.getNCOMMID()), 13)
//
//
//                );
//
//                mUsbThermalPrinter.addString(
//                        setStringLength(String.valueOf(bean.getGoodsCount()), 3)
//                                + "          " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1))
//                                + "          " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount()))
//
//                );
//            }
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.addString("合计：" + new DecimalFormat("#0.00").format(money) + "元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("支付类型:" + payType);
//            mUsbThermalPrinter.addString("购买数量:" + String.valueOf(totalCount));
//            mUsbThermalPrinter.addString("付款金额:" + new DecimalFormat("#0.00").format(money) + " 元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.printString();
//
//            Bitmap bitmap = CreateCode(orderNo, BarcodeFormat.QR_CODE, 160, 160);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.printLogo(bitmap, false);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("请妥善保存本小票");
//            mUsbThermalPrinter.printString();
//            mUsbThermalPrinter.walkPaper(10);
//        } catch (Exception e) {
//
//        } finally {
//            mUsbThermalPrinter.stop();
//        }
//    }
//
//    public static void TpsN900Print_TicketAll(Context context, List<TicketScanBean> ticketprintList) {
//        for (int k = 0; k < Integer.valueOf(Constant.CHECK_OUT); k++) {
//            UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//            try {
//                mUsbThermalPrinter.start(0);
//                mUsbThermalPrinter.reset();
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString("\n" + "核销信息");
//                mUsbThermalPrinter.addString("-------------------------");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(5);
//                mUsbThermalPrinter.addString("核销时间:  " + TimeUtils.time());
//                mUsbThermalPrinter.addString("-------------------------");
//                for (int i = 0; i < ticketprintList.size(); i++) {
//                    TicketScanBean bean = ticketprintList.get(i);
//                    String name = bean.getTicketName();
//                    mUsbThermalPrinter.addString("门票名称: " + setStringLength(name, 16));
//                    mUsbThermalPrinter.addString("\n核销数量: " + bean.getTicketdata().size() + " 张");
//                    int count = 0;
//                    for (int j = 0; j < bean.getTicketdata().size(); j++) {
//                        TicketScanBean.TicketBean ticketbean = bean.getTicketdata().get(j);
//                        count += Integer.valueOf(ticketbean.getReallyPeople());
//                    }
//                    mUsbThermalPrinter.addString("\n入园人数: " + count + " 人");
//                    double price = Double.valueOf(bean.getTicketdata().get(0).getTicketPrice());
//                    mUsbThermalPrinter.addString("\n门票单价: " + price + " 元");
//                    mUsbThermalPrinter.addString("\n门票总价: " + price * bean.getTicketdata().size() + " 元");
//                    mUsbThermalPrinter.addString("-------------------------");
//                }
//                mUsbThermalPrinter.addString("请妥善保存本小票");
//                mUsbThermalPrinter.printString();
//                mUsbThermalPrinter.walkPaper(10);
//            } catch (Exception e) {
//
//            } finally {
//                mUsbThermalPrinter.stop();
//            }
//        }
//    }
//
//    public static void TpsN900OneCardPrint_Food(Context context, ArrayList<DetailBean.DtBean> list, double money, String orderNo, String payType, String cardNo, double cardMoney, String tableNo, String tableName) throws Exception {
//        int totalCount = 0;
//        for (int i = 0; i < list.size(); i++) {
//            DetailBean.DtBean detailBean = list.get(i);
//            totalCount += detailBean.getGoodsCount();
//        }
//        UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//        try {
//
//            mUsbThermalPrinter.start(0);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("\n" + "餐饮清单");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setGray(5);
//            mUsbThermalPrinter.addString("消费时间:  " + TimeUtils.time());
//            mUsbThermalPrinter.addString("收银   员:  " + Constant.USERNAME);
//            mUsbThermalPrinter.addString("订单编号:  " + orderNo);
//            mUsbThermalPrinter.addString("桌台编号:  " + tableNo);
//            mUsbThermalPrinter.addString("桌台名称:  " + tableName);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.addStringOffset(0, "名称/条码");
//            mUsbThermalPrinter.addStringOffset(160, "数量");
//            mUsbThermalPrinter.addStringOffset(240, "价格");
//            mUsbThermalPrinter.addStringOffset(320, "小计");
//            mUsbThermalPrinter.endLine();
//            LogUtil.i("TpsN900PrintUtils", "list的大小--->：" + list.size());
//
//            for (int i = 0; i < list.size(); i++) {
//                DetailBean.DtBean bean = list.get(i);
//                mUsbThermalPrinter.addString(
//                        setStringLength(bean.getVCOMMYNAME(), 16)
//                                + setStringLength(String.valueOf(bean.getNCOMMID()), 13)
//                );
//                mUsbThermalPrinter.addString(
//                        setStringLength(String.valueOf(bean.getGoodsCount()), 3)
//                                + "          " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1))
//                                + "          " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount()))
//
//                );
//            }
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居中
//            mUsbThermalPrinter.addString("合计：" + new DecimalFormat("#0.00").format(money) + "元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("支付类型:" + payType);
//            mUsbThermalPrinter.addString("购买数量:" + String.valueOf(totalCount));
//            mUsbThermalPrinter.addString("付款金额:" + new DecimalFormat("#0.00").format(money) + " 元");
//            mUsbThermalPrinter.addString("支付卡号:" + cardNo);
//            mUsbThermalPrinter.addString("卡内余额:" + new DecimalFormat("#0.00").format(cardMoney));
//
//            mUsbThermalPrinter.addString("-------------------------");
//            Bitmap bitmap = CreateCode(orderNo, BarcodeFormat.QR_CODE, 160, 160);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setGray(3);
//            mUsbThermalPrinter.printLogo(bitmap, true);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("请妥善保存本小票");
//            mUsbThermalPrinter.printString();
//            mUsbThermalPrinter.walkPaper(10);
//
//        } catch (Exception e) {
//
//        } finally {
//            mUsbThermalPrinter.stop();
//        }
//    }
//
//    /**
//     * @author zxh
//     * created at 2017/6/27 13:29
//     * 方法名: TpsN900ReturnPrint
//     * 方法说明: 商品退货打印清单
//     */
//    public static void TpsN900ReturnPrint(Context context, ArrayList<ReturnBean> list, String money, String orderNo, String newOrder, String payType) throws Exception {
//        int totalCount = 0;
//        ReturnBean bean01 = list.get(0);
//        String vcommyname = bean01.getVCOMMYNAME();
//        for (int i = 0; i < list.size(); i++) {
//            ReturnBean returnBean = list.get(i);
//            totalCount += returnBean.getRCANNUMBER();
//        }
//        UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//        try {
//
//            mUsbThermalPrinter.start(0);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("\n" + "退货清单");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("退货时间:  " + TimeUtils.time_S());
//            mUsbThermalPrinter.addString("收银   员:  " + Constant.USERNAME);
//            mUsbThermalPrinter.addString("原订单号:  " + orderNo);
//            mUsbThermalPrinter.addString("退货单号:  " + newOrder);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("名称/" + "条码" + "    单价    数量    小计");
//            LogUtil.i("TpsN900PrintUtils", "list的大小--->：" + list.size());
//
//            for (int i = 0; i < list.size(); i++) {
//                ReturnBean bean = list.get(i);
//                mUsbThermalPrinter.addString(setStringLength(bean.getVCOMMYNAME(), 16)
//                );
//                mUsbThermalPrinter.addString(
//                        setStringLength(String.valueOf(bean.getNCOMMID()), 4)
//                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getNPRICE(), 1))
//                                + "     " + setStringLength(String.valueOf(bean.getRCANNUMBER()), 3)
//                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getNPRICE(), bean.getRCANNUMBER()))
//                );
//            }
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.addString("合计：" + money + "元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("支付类型:" + payType);
//            mUsbThermalPrinter.addString("退货数量:" + String.valueOf(totalCount));
//            mUsbThermalPrinter.addString("退款金额:" + money + " 元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.printString();
//
//            Bitmap bitmap = CreateCode(orderNo, BarcodeFormat.QR_CODE, 160, 160);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.printLogo(bitmap, false);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("请妥善保存本小票");
//            mUsbThermalPrinter.printString();
//            mUsbThermalPrinter.walkPaper(10);
//        } catch (Exception e) {
//
//        } finally {
//            mUsbThermalPrinter.stop();
//        }
//    }
//
//    /**
//     * @author zxh
//     * created at 2017/6/27 13:29
//     * 方法名: TpsN900Print_ticket
//     * 方法说明: 门票购票打印,以及补打上笔门票,包括门票核销小票
//     */
//    public static void TpsN900Print_ticket(Context context, String title, String ticketName, String qrcodeStr, String sprintdescch) {
//        for (int i = 0; i < Integer.valueOf(Constant.CHECK_OUT); i++) {
//            UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//            try {
//                mUsbThermalPrinter.start(0);
//                mUsbThermalPrinter.reset();
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.addString(title + "\n");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.addString(ticketName);
//                Bitmap bitmap = CreateCode(qrcodeStr, BarcodeFormat.QR_CODE, 256, 256);
//                if (bitmap != null) {
//                    mUsbThermalPrinter.printLogo(bitmap, true);
//                }
//
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.addString("\n" + sprintdescch);
//                mUsbThermalPrinter.printString();
//                mUsbThermalPrinter.walkPaper(10);
//            } catch (Exception e) {
//
//            } finally {
//
//                mUsbThermalPrinter.stop();
//            }
//
//        }
//
//    }
//
//
//    public static void TpsN900PayPrint(Context context, String ticketName, String qrcodeStr) {
//        for (int i = 0; i < Integer.valueOf(Constant.CHECK_OUT); i++) {
//            UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//            try {
//                mUsbThermalPrinter.start(0);
//                mUsbThermalPrinter.reset();
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString("\n" + "消费支付清单" + "\n");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString(ticketName);
//                Bitmap bitmap = CreateCode(qrcodeStr, BarcodeFormat.QR_CODE, 256, 256);
//                if (bitmap != null) {
//                    mUsbThermalPrinter.printLogo(bitmap, true);
//                }
//
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString("\n" + "本小票只作为消费凭证,不作为报销凭证");
//                mUsbThermalPrinter.printString();
//                mUsbThermalPrinter.walkPaper(10);
//
//            } catch (Exception e) {
//
//            } finally {
//                mUsbThermalPrinter.stop();
//            }
//
//
//        }
//    }
//
//    /**
//     * todo 带团订单打印
//     *
//     * @param teamOrders
//     * @throws Exception
//     */
//    public static void PrintTeamTicket(Context context, List<TeamBean_1.DataBean.TeamOrdersBean> teamOrders, String countMoney) throws Exception {
//        UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//        try {
//            TeamBean_1.DataBean.TeamOrdersBean bean = teamOrders.get(0);
//            SimpleDateFormat formatter01 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
//            Date curDate01 = new Date(System.currentTimeMillis());
//            String strDate01 = formatter01.format(curDate01);
//            int checkOutPrint = Integer.parseInt(Constant.CHECK_OUT);
//            for (int i = 0; i < checkOutPrint; i++) {
//                mUsbThermalPrinter.start(0);
//                mUsbThermalPrinter.reset();
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString("\n带团兑票单"
//                        + "\n-------------------------"
//                );
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString(
//                        "\n                            "
//                                + "\n  名称    " + "       价格      " + "张数"
//                );
//
//
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                for (int x = 0; x < teamOrders.size(); x++) {
//                    TeamBean_1.DataBean.TeamOrdersBean teamOrdersBean = teamOrders.get(x);
//                    mUsbThermalPrinter.addString("\n" + teamOrdersBean.getPRODUCTNAME() + "    " + "       " + teamOrdersBean.getPRODUCTPRICE() + "      " + teamOrdersBean.getActualPerson());
//                }
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString(
//                        "\n实付总额: " + countMoney + "\n" +
//                                "\n门票来源: " + bean.getPARTNERNAME() + "\n" +
//                                "\n可用日期: " + bean.getPLAYDATE()
//                                + "\n                            "
//                                + "\n结束日期  " + bean.getPLAYDATE()
//                                + "\n                            "
//                                + "\n兑票时间: " + strDate01
//                                + "\n                            "
//                                + "\n门票串码: " + bean.getECODE()
//                                + "\n");
//
//                Bitmap bitmap = CreateCode(bean.getECODE(), BarcodeFormat.QR_CODE, 256, 256);
//                if (bitmap != null) {
//                    mUsbThermalPrinter.printLogo(bitmap, true);
//                }
//
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString("\n" + "本小票只作为兑票凭证,不作为报销凭证");
//                mUsbThermalPrinter.printString();
//                mUsbThermalPrinter.walkPaper(10);
//
//
//            }
//        } catch (Exception e) {
//
//        } finally {
//            mUsbThermalPrinter.stop();
//        }
//    }
//
//    /**
//     * @param name   需要变动的字符串
//     * @param length name想要的长度
//     * @return
//     */
//    public static String setStringLength(String name, int length) {
//
//
//        String newString = "";
//        int length_name = name.length();
//        LogUtil.i("setStringLength", "length_name---->" + length_name);
//
//        if (length_name > length) {
//            newString = name.substring(0, length);
//        } else {
//
//            newString = String.format("%-" + length + "s", name);
//        }
//
//        LogUtil.i("setStringLength", "newString----->" + newString.length());
//        return newString;
//    }
//
//    /**
//     * 汇总打印
//     *
//     * @param payList
//     * @param totalPayMoney
//     * @param returnList
//     * @param totalReturnMoney
//     * @param currentList
//     * @param totalCurrentMoney
//     * @param counterName
//     * @param sellerId
//     * @param sellerName
//     * @param sellDate
//     * @param printDate
//     * @throws Exception
//     */
//    public static void TpsN900FormPrint(Context context, String Type, String ticketNumber, String ticketPrice,
//                                        ArrayList<SalaTicketFormsBean> PaytypeList, String PayNumber, String PayPrice,
//                                        ArrayList<FormBean> payList
//            , String totalPayMoney, ArrayList<FormBean> returnList, String totalReturnMoney, ArrayList<FormBean> currentList, String totalCurrentMoney, String counterName, String sellerId, String sellerName, String sellDate, String printDate) throws Exception {
//        UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//        for (int j = 0; j < Integer.valueOf(Constant.CHECK_OUT); j++) {
//            try {
//
//                mUsbThermalPrinter.start(0);
//                mUsbThermalPrinter.reset();
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.setLeftIndent(0);
//                mUsbThermalPrinter.setLineSpace(0);
//                mUsbThermalPrinter.setFontSize(2);
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString(Type);
//                mUsbThermalPrinter.addString("-------------------------");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.setGray(7);
//                mUsbThermalPrinter.addString("收银柜台:  " + counterName);
//                mUsbThermalPrinter.addString("收银员ID:  " + sellerId);
//                mUsbThermalPrinter.addString("收银    员:  " + sellerName);
//                mUsbThermalPrinter.addString("销售日期:  " + sellDate);
//                mUsbThermalPrinter.addString("打印日期:  " + printDate);
//
//                mUsbThermalPrinter.addString("-------------------------");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.addString("消费支付");
//                mUsbThermalPrinter.addString("-------------------------");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.addString("支付类型        支付金额(元)");
//                for (int i = 0; i < payList.size(); i++) {
//                    FormBean bean = payList.get(i);
//                    if ("微信".equals(bean.getSMONEYNAMECN())) {
//                        if (Double.parseDouble(bean.getNAMOUNT()) > 1.00) {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "               " + new DecimalFormat("#0.00").format(Double.parseDouble(bean.getNAMOUNT()))
//                            );
//                        } else {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "               " + bean.getNAMOUNT()
//                            );
//                        }
//
//                    } else {
//                        if (Double.parseDouble(bean.getNAMOUNT()) > 1.00) {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "            " + new DecimalFormat("#0.00").format(Double.parseDouble(bean.getNAMOUNT()))
//                            );
//                        } else {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "            " + bean.getNAMOUNT()
//                            );
//                        }
//
//                    }
//                }
//                mUsbThermalPrinter.addString("合计：" + totalPayMoney + "元");
//
//                mUsbThermalPrinter.addString("-------------------------");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.addString("退款");
//                mUsbThermalPrinter.addString("-------------------------");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.addString("退款类型        退款金额(元)");
//                for (int i = 0; i < returnList.size(); i++) {
//                    FormBean bean = returnList.get(i);
//                    if ("微信".equals(bean.getSMONEYNAMECN())) {
//                        if (Double.parseDouble(bean.getNAMOUNT()) > 1.00) {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "               " + new DecimalFormat("#0.00").format(Double.parseDouble(bean.getNAMOUNT()))
//                            );
//                        } else {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "               " + bean.getNAMOUNT()
//                            );
//                        }
//
//                    } else {
//                        if (Double.parseDouble(bean.getNAMOUNT()) > 1.00) {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "            " + new DecimalFormat("#0.00").format(Double.parseDouble(bean.getNAMOUNT()))
//                            );
//                        } else {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "            " + bean.getNAMOUNT()
//                            );
//                        }
//
//                    }
//                }
//                mUsbThermalPrinter.addString("合计：" + totalReturnMoney + "元");
//
//                mUsbThermalPrinter.addString("-------------------------");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//                mUsbThermalPrinter.addString("当日合计");
//                mUsbThermalPrinter.addString("-------------------------");
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//                mUsbThermalPrinter.addString("支付类型        支付金额(元)");
//                for (int i = 0; i < currentList.size(); i++) {
//                    FormBean bean = currentList.get(i);
//                    if ("微信".equals(bean.getSMONEYNAMECN())) {
//                        if (Double.parseDouble(bean.getNAMOUNT()) > 1.00) {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "               " + new DecimalFormat("#0.00").format(Double.parseDouble(bean.getNAMOUNT()))
//                            );
//                        } else {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "               " + bean.getNAMOUNT()
//                            );
//                        }
//
//                    } else {
//                        if (Double.parseDouble(bean.getNAMOUNT()) > 1.00) {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "            " + new DecimalFormat("#0.00").format(Double.parseDouble(bean.getNAMOUNT()))
//                            );
//                        } else {
//                            mUsbThermalPrinter.addString(
//                                    bean.getSMONEYNAMECN() + "            " + bean.getNAMOUNT()
//                            );
//                        }
//
//                    }
//
//                }
//                mUsbThermalPrinter.addString("合计：" + totalCurrentMoney + "元");
//
//                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居中
////                mUsbThermalPrinter.addString("收银柜台:  " + Constant.S_devicename);
////                mUsbThermalPrinter.addString("收银员ID:  " + Constant.S_deviceID);
////                mUsbThermalPrinter.addString("收银    员:  " + Constant.S_devicename);
////                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
////                mUsbThermalPrinter.addString("-------------------------");
////                mUsbThermalPrinter.addString("线下门票核销");
////                mUsbThermalPrinter.addString("-------------------------");
////                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
////                mUsbThermalPrinter.addString("核销总计        价格总计");
////                mUsbThermalPrinter.addString(ticketNumber + "         " + ticketPrice);
////                mUsbThermalPrinter.addString("-------------------------");
////                mUsbThermalPrinter.addString("线下门票售卖");
////                mUsbThermalPrinter.addString("-------------------------");
////                mUsbThermalPrinter.addString("付款方式        金额小计");
////                for (int i = 0; i < PaytypeList.size(); i++) {
////                    mUsbThermalPrinter.addString(PaytypeList.get(i).getPAYMENTSTYLE() + "        " + OtherUtils.doubleprice(PaytypeList.get(i).getITEMMONEY()));
////                }
////                mUsbThermalPrinter.addString("-------------------------");
////                mUsbThermalPrinter.addString("购买总计        价格总计");
////                mUsbThermalPrinter.addString(PayNumber + "         " + PayPrice);
//                mUsbThermalPrinter.addString("-------------------------");
//                mUsbThermalPrinter.addString("请妥善保存本小票");
//                mUsbThermalPrinter.printString();
//                mUsbThermalPrinter.walkPaper(10);
//
//            } catch (Exception e) {
//
//            } finally {
//                mUsbThermalPrinter.stop();
//            }
//        }
//    }
//
//
//    /**
//     * @param str
//     * @param type
//     * @param bmpWidth
//     * @param bmpHeight
//     * @return
//     * @throws WriterException
//     */
//    public static Bitmap CreateCode(String str, BarcodeFormat type, int bmpWidth, int bmpHeight) throws WriterException {
//        // 生成二维矩阵,编码时要指定大小,不要生成了图片以后再进行缩放,以防模糊导致识别失败
//        BitMatrix matrix = new MultiFormatWriter().encode(str, type, bmpWidth, bmpHeight);
//        int width = matrix.getWidth();
//        int height = matrix.getHeight();
//        // 二维矩阵转为一维像素数组（一直横着排）
//        int[] pixels = new int[width * height];
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                if (matrix.get(x, y)) {
//                    pixels[y * width + x] = 0xff000000;
//                } else {
//                    pixels[y * width + x] = 0xffffffff;
//                }
//            }
//        }
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        // 通过像素数组生成bitmap,具体参考api
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }
//
//    public static void TpsN900OneCardPrint_Food_2(Context context, ArrayList<DetailBean.DtBean> list, double money, String orderNo, String payType, String cardNo, double cardMoney, String tableNo, String tablePeople) throws Exception {
//        int totalCount = 0;
//        for (int i = 0; i < list.size(); i++) {
//            DetailBean.DtBean detailBean = list.get(i);
//            totalCount += detailBean.getGoodsCount();
//        }
//        UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//        try {
//
//            mUsbThermalPrinter.start(0);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("\n" + "餐饮清单");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setGray(5);
//            mUsbThermalPrinter.addString("消费时间:  " + TimeUtils.time());
//            mUsbThermalPrinter.addString("收银   员:  " + Constant.USERNAME);
//            mUsbThermalPrinter.addString("订单编号:  " + orderNo);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
////            mUsbThermalPrinter.addString("名称/" + "条码" + "   单价   数量   小计");
//            mUsbThermalPrinter.addStringOffset(0, "名称/条码");
//            mUsbThermalPrinter.addStringOffset(160, "数量");
//            mUsbThermalPrinter.addStringOffset(240, "价格");
//            mUsbThermalPrinter.addStringOffset(320, "小计");
//            mUsbThermalPrinter.endLine();
//            LogUtil.i("TpsN900PrintUtils", "list的大小--->：" + list.size());
//
//            for (int i = 0; i < list.size(); i++) {
//                DetailBean.DtBean bean = list.get(i);
////                mUsbThermalPrinter.addString(setStringLength(bean.getVCOMMYNAME(), 16));
////                mUsbThermalPrinter.addString(
////                        setStringLength(bean.getNCOMMID(), 6)
////                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1))
////                                + "     " + String.valueOf(bean.getGoodsCount())
////                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount()))
////                );
//                mUsbThermalPrinter.addStringOffset(0, bean.getVCOMMYNAME());
//                mUsbThermalPrinter.endLine();
//                mUsbThermalPrinter.addStringOffset(0, bean.getNCOMMID());
//                mUsbThermalPrinter.addStringOffset(160, String.valueOf(bean.getGoodsCount()));
//                mUsbThermalPrinter.addStringOffset(240, new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1)));
//                mUsbThermalPrinter.addStringOffset(320, new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount())));
//                mUsbThermalPrinter.endLine();
//            }
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居中
//            mUsbThermalPrinter.addString("合计：" + new DecimalFormat("#0.00").format(money) + "元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("支付类型:" + payType);
//            mUsbThermalPrinter.addString("购买数量:" + String.valueOf(totalCount));
//            mUsbThermalPrinter.addString("付款金额:" + new DecimalFormat("#0.00").format(money) + " 元");
//            mUsbThermalPrinter.addString("支付卡号:" + cardNo);
//            mUsbThermalPrinter.addString("卡内余额:" + new DecimalFormat("#0.00").format(cardMoney));
//
//            mUsbThermalPrinter.addString("-------------------------");
//            Bitmap bitmap = CreateCode(orderNo, BarcodeFormat.QR_CODE, 160, 160);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setGray(3);
//            mUsbThermalPrinter.printLogo(bitmap, true);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("请妥善保存本小票");
//            mUsbThermalPrinter.printString();
//            mUsbThermalPrinter.walkPaper(10);
//
//        } catch (Exception e) {
//
//        } finally {
//            mUsbThermalPrinter.stop();
//        }
//    }
//
//    public static void TpsN900Print_Food_2(Context context, ArrayList<DetailBean.DtBean> list, double money, String orderNo, String payType, String tableNo, String tablePeople) throws Exception {
//        int totalCount = 0;
//        for (int i = 0; i < list.size(); i++) {
//            DetailBean.DtBean detailBean = list.get(i);
//            totalCount += detailBean.getGoodsCount();
//        }
//        UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(context);
//        try {
//
//            mUsbThermalPrinter.start(0);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("\n" + "餐饮清单");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(5);
//            mUsbThermalPrinter.addString("消费时间:  " + TimeUtils.time());
//            mUsbThermalPrinter.addString("收银   员:  " + Constant.USERNAME);
//            mUsbThermalPrinter.addString("订单编号:  " + orderNo);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);//设置居左
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(5);
////            mUsbThermalPrinter.addString("名称/" + "条码" + "   单价   数量   小计");
//            mUsbThermalPrinter.addStringOffset(0, "名称/条码");
//            mUsbThermalPrinter.addStringOffset(160, "数量");
//            mUsbThermalPrinter.addStringOffset(240, "价格");
//            mUsbThermalPrinter.addStringOffset(320, "小计");
//            mUsbThermalPrinter.endLine();
//            LogUtil.i("TpsN900PrintUtils", "list的大小--->：" + list.size());
//
//            for (int i = 0; i < list.size(); i++) {
//                DetailBean.DtBean bean = list.get(i);
////                mUsbThermalPrinter.addString(setStringLength(bean.getVCOMMYNAME(), 16));
////                mUsbThermalPrinter.addString(
////                        setStringLength(bean.getNCOMMID(), 6)
////                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1))
////                                + "     " + String.valueOf(bean.getGoodsCount())
////                                + "     " + new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount()))
////                );
//                mUsbThermalPrinter.addStringOffset(0, bean.getVCOMMYNAME());
//                mUsbThermalPrinter.endLine();
//                mUsbThermalPrinter.addStringOffset(0, bean.getNCOMMID());
//                mUsbThermalPrinter.addStringOffset(160, String.valueOf(bean.getGoodsCount()));
//                mUsbThermalPrinter.addStringOffset(240, new DecimalFormat("#0.00").format(mul(bean.getPRICE(), 1)));
//                mUsbThermalPrinter.addStringOffset(320, new DecimalFormat("#0.00").format(mul(bean.getPRICE(), bean.getGoodsCount())));
//                mUsbThermalPrinter.endLine();
//            }
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.addString("合计：" + new DecimalFormat("#0.00").format(money) + "元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("支付类型:" + payType);
//            mUsbThermalPrinter.addString("购买数量:" + String.valueOf(totalCount));
//            mUsbThermalPrinter.addString("付款金额:" + new DecimalFormat("#0.00").format(money) + " 元");
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.printString();
//
//            Bitmap bitmap = CreateCode(orderNo, BarcodeFormat.QR_CODE, 160, 160);
//            mUsbThermalPrinter.reset();
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.printLogo(bitmap, false);
//            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);//设置居中
//            mUsbThermalPrinter.setLeftIndent(0);
//            mUsbThermalPrinter.setLineSpace(0);
//            mUsbThermalPrinter.setFontSize(2);
//            mUsbThermalPrinter.setGray(7);
//            mUsbThermalPrinter.addString("-------------------------");
//            mUsbThermalPrinter.addString("请妥善保存本小票");
//            mUsbThermalPrinter.printString();
//            mUsbThermalPrinter.walkPaper(10);
//        } catch (Exception e) {
//
//        } finally {
//            mUsbThermalPrinter.stop();
//        }
//    }
}
