package com.yz.internalcommon.util;

import java.math.BigDecimal;

/**
 * @Author: yangzhen
 * @Date 2022/10/26-19:09
 * @Description: com.yz.internalcommon.util
 * @version: 1.0
 */
public class BigDecimalUtils {

    /**
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1,double v2){
        BigDecimal vb1 = BigDecimal.valueOf(v1);
        BigDecimal vb2 = BigDecimal.valueOf(v2);
        return vb1.add(vb2).doubleValue();
    }


    /**
     * 减法
     * @param v1
     * @param v2
     * @return
     */
    public static double subtract(double v1,double v2){
        BigDecimal vb1 = BigDecimal.valueOf(v1);
        BigDecimal vb2 = BigDecimal.valueOf(v2);
        return vb1.subtract(vb2).doubleValue();
    }

    /**
     * 乘法
     * @param v1
     * @param v2
     * @return
     */
    public static double multiply(double v1,double v2){
        BigDecimal vb1 = BigDecimal.valueOf(v1);
        BigDecimal vb2 = BigDecimal.valueOf(v2);
        return vb1.multiply(vb2).doubleValue();
    }


    /**
     * 除法
     * @param v1
     * @param v2
     * @return
     */
    public static double divide(int v1,int v2){
        if(v2<=0){
            throw new IllegalArgumentException("除数应为正数");
        }
        BigDecimal vb1 = BigDecimal.valueOf(v1);
        BigDecimal vb2 = BigDecimal.valueOf(v2);
        return vb1.divide(vb2,2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
