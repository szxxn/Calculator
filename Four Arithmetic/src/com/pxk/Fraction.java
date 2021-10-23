package com.pxk;

import java.util.Random;

/**
 * @author pxk
 * <p>
 * 分数类
 */
public class Fraction {

    /**
     * 分子
     */
    private int numerator;

    /**
     * 分母
     */
    private int denominator;

    /**
     * 此构造方法用于系统的随机生成分数
     *
     * @param range 取值范围
     */
    public Fraction(int range) {
        Random random = new Random();
        denominator = random.nextInt(range);
        if (this.denominator == 0) {
            denominator = random.nextInt();
        } else {
            numerator = random.nextInt(denominator * range);
        }
    }

    /**
     * 获取分数的值
     *
     * @return 分数的值
     */
    public double getValue() {
        return numerator / denominator;
    }

    @Override
    public String toString() {
        if (numerator % denominator == 0) {
            return ((numerator / denominator) + "");
        } else if (numerator < denominator) {
            return (numerator + "/" + denominator);
        } else {
            return (""+(numerator / denominator) + "'" + (numerator % denominator) + "/" + denominator);
        }
    }

}
