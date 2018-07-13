package com.example.tps900.tps900.Utlis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wo on 2017/5/10.
 */

public class StrMatchUtils {

    public static boolean isChineseForStr(String str){

        Pattern p=Pattern.compile("[\u4e00-\u9fa5]*");
        Matcher m=p.matcher(str);
        if(m.matches()){
            return true;
        }

        return false;
    }

    public static boolean isNumberForStr(String str){

        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        if(m.matches() ){
            return true;
        }

        return false;
    }


//    public static boolean isWhatKindForStr02(String str){
//
//        Pattern p = Pattern.compile("[0-9]*");
//        Matcher m = p.matcher(str);
//        if(m.matches() ){
//        }
//        p=Pattern.compile("[a-zA-Z]");
//        m=p.matcher(str);
//        if(m.matches()){
//        }
//        p=Pattern.compile("[\u4e00-\u9fa5]");
//        m=p.matcher(str);
//        if(m.matches()){
//        }
//
//        return false;
//    }
public static boolean isEnglishForStr(String str){
    Pattern p=Pattern.compile("[a-zA-Z]*");
    Matcher m=p.matcher(str);
    if(m.matches()){
        return true;
    }

    return false;
}

}
