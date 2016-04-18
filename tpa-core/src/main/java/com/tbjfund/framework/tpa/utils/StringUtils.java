package com.tbjfund.framework.tpa.utils;

import com.alibaba.druid.sql.visitor.functions.Char;

/**
 * Created by sidawei on 16/4/17.
 */
public class StringUtils {

    /**
     * com.tbjfund.framework.StringUtils ==> StringUtils
     * @param s
     * @return
     */
    public static String getSimpleName(String s){
        if (s.indexOf(".") != -1){
            return s.substring(s.lastIndexOf(".") + 1);
        }
        return s;
    }

    /**
     * com.tbjfund.framework.StringUtils ==> com.tbjfund.framework
     * @param s
     * @return
     */
    public static String getPackageName(String s){
        if (s.indexOf(".") != -1){
            return s.substring(0, s.lastIndexOf("."));
        }
        return s;
    }

    /**
     * UserModel ==> userModel
     * @param s
     * @return
     */
    public static String getFistLowName(String s){
        StringBuffer sb = new StringBuffer(s);
        sb.setCharAt(0, String.valueOf(sb.charAt(0)).toLowerCase().charAt(0));
        return sb.toString();
    }

    /**
     * userId ==> user_id
     * UserId ==> user_id
     * @param s
     * @return
     */
    public static String getNormalName(String s){
        if (s == null || s.length() == 0){
            return s;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (Character.isUpperCase(c)){
                if (sb.length() == 0){
                    sb.append(Character.toLowerCase(c));
                }else {
                    sb.append("_" + Character.toLowerCase(c));
                }
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
