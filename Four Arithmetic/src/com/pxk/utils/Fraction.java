package com.pxk.utils;

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
     * 此构造方法用于处理计算结果
     *
     * @param numerator   分子
     * @param denominator 分母
     */
    public Fraction(int numerator, int denominator) {
        if (numerator != 0) {
            int a = simplification(numerator, denominator);
            this.numerator = numerator / a;
            this.denominator = denominator / a;
        } else {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    /**
     * 此构造方法用于系统的随机生成分数
     *
     * @param range 分数的取值范围
     */
    public Fraction(int range) {
        Random random = new Random();
        denominator = random.nextInt(range);
        while (denominator == 0) {
            denominator = random.nextInt(range);
        }
        numerator = random.nextInt(denominator * range);

        // 化简
        if (numerator != 0) {
            int a = simplification(numerator, denominator);
            numerator = numerator / a;
            denominator = denominator / a;
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

    /**
     * 得到两个数的最大公约数
     *
     * @param a
     * @param b
     * @return 最大公约数
     */
    public int simplification(int a, int b) {
        if (a != 0 && b != 0) {
            if (a < b) { // 将较大的数字放在上面
                int temp = b;
                b = a;
                a = temp;
            }
            int r = a % b;
            while (r != 0) {
                a = b;
                b = r;
                r = a % b;
            }
        }
        return b;
    }

    /**
     * 加法
     *
     * @param f1
     * @param f2
     * @return
     */
    public Fraction add(Fraction f1, Fraction f2) {
        Fraction res = new Fraction(1, 1);
        res.numerator = f1.numerator * f2.denominator + f2.numerator * f1.denominator;
        res.denominator = f1.denominator * f2.denominator;

        int a = simplification(res.numerator, res.denominator);
        res.numerator = res.numerator / a;
        res.denominator = res.denominator / a;

        return res;
    }

    /**
     * 减法
     *
     * @param f1
     * @param f2
     * @return
     */
    public Fraction sub(Fraction f1, Fraction f2) {
        Fraction res = new Fraction(1, 1);
        res.numerator = f1.numerator * f2.denominator - f2.numerator * f1.denominator;
        res.denominator = f1.denominator * f2.denominator;

        int a = simplification(res.numerator, res.denominator);
        res.numerator = res.numerator / a;
        res.denominator = res.denominator / a;

        return res;
    }

    /**
     * 除法
     *
     * @param f1
     * @param f2
     * @return
     */
    public Fraction div(Fraction f1, Fraction f2) throws Exception{
        Fraction res = new Fraction(1, 1);
        res.numerator = f1.numerator * f2.denominator;
        res.denominator = f2.numerator * f1.denominator;
        if (res.denominator == 0) { // 计算过程中可能会出现 1 除于 （5-4-1），此时只要出现除于0，就重新生成式子
            throw new Exception("被除数为0！");
        }
        int a = simplification(res.numerator, res.denominator);
        res.numerator = res.numerator / a;
        res.denominator = res.denominator / a;

        return res;
    }

    /**
     * 乘法
     *
     * @param f1
     * @param f2
     * @return
     */
    public Fraction mul(Fraction f1, Fraction f2) {
        Fraction res = new Fraction(1, 1);
        res.numerator = f1.numerator * f2.numerator;
        res.denominator = f2.denominator * f1.denominator;

        int a = simplification(res.numerator, res.denominator);
        res.numerator = res.numerator / a;
        res.denominator = res.denominator / a;

        return res;
    }

    @Override
    public String toString() {
        if (numerator % denominator == 0) {
            return ((numerator / denominator) + "");
        } else if (numerator < denominator) {
            return (numerator + "/" + denominator);
        } else {
            return ("" + (numerator / denominator) + "'" + (numerator % denominator) + "/" + denominator);
        }
    }


}
