package com.tbjfund.framework.tpa.utils;

/**
 * Created by sidawei on 16/4/17.
 */
public class StringUtils {

    public static String getSimpleName(String s){
        if (s.indexOf(".") != -1){
            return s.substring(s.lastIndexOf(".") + 1);
        }
        return s;
    }

    public static String getPackageName(String s){
        if (s.indexOf(".") != -1){
            return s.substring(0, s.lastIndexOf("."));
        }
        return s;
    }

    public static String getFistLowName(String s){
        StringBuffer sb = new StringBuffer(s);
        sb.setCharAt(0, String.valueOf(sb.charAt(0)).toLowerCase().charAt(0));
        return sb.toString();
    }

}
