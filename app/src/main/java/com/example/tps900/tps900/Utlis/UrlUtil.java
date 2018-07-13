package com.example.tps900.tps900.Utlis;

import java.io.UnsupportedEncodingException;

/**
 * 项目名称：CQPda550_NEW
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/22 17:00
 * 修改人：zxh
 * 修改时间：2017/3/22 17:00
 * 修改备注：
 */

public class UrlUtil {
    private final static String ENCODE = "UTF-8";

    /**
     * URL 解码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:09:51
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * URL 转码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:10:28
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @return void
     * @author lifq
     * @date 2015-3-17 下午04:09:16
     */
    public static void main(String[] args) {
        String str = "001";
        System.out.println(getURLEncoderString(str));
        System.out.println(getURLDecoderString(str));

    }

}
