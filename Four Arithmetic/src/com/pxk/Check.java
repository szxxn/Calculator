package com.pxk;

import java.util.List;

/**
 * @author pxk
 * @date 2021年10月23日 21:01
 * <p>
 * 检查是否生成重复的计算式
 */
public class Check {

    /**
     * 判断一个字符串是否为运算符
     *
     * @param str 运算符
     * @return true是为真，false不是为假
     */
    public static boolean isOperator(String str) {
        if (str.equals("+")
                || str.equals("-")
                || str.equals("÷")
                || str.equals("×")) {
            return true;
        }
        return false;

    }

    /**
     * 判断两条计算式是否能通过有限次数的 ×+ 交换得到相同的式子
     *
     * @param expression_1 表达式1
     * @param expression_2 表达式2
     * @return false表示不能，true表示可以
     */
    public static boolean isSimilar(String expression_1, String expression_2) {
        List<String> list1 = Calculation.getSuffixExpressionList(expression_1);
        List<String> list2 = Calculation.getSuffixExpressionList(expression_2);

        if (list1.size() != list2.size()) {    // 两条算式的长度不同
            return false;
        } else {
            for (int i = 0; i < list1.size(); i++) {
                if (isOperator(list1.get(i))) {
                    if (isOperator(list2.get(i))
                            && list1.get(i).equals(list2.get(i))) {  // 相同的位置出现运算符，并且运算符相同
                        if (list1.get(i).equals("-")
                                || list1.get(i).equals("÷")) {  // 当运算符是 - ÷ 时，前面两个数字必须相同
                            return list1.get(i - 1).equals(list2.get(i - 1))
                                    && list1.get(i - 2).equals(list2.get(i - 2));
                        }
                        if (list1.get(i).equals("+")
                                || list1.get(i).equals("×")) {  // 当运算符是 + × 时，前面两个数字可以顺序相同，也可以顺序相反
                            return (list1.get(i - 1).equals(list2.get(i - 1)) && list1.get(i - 2).equals(list2.get(i - 2)))
                                    || (list1.get(i - 1).equals(list2.get(i - 2)) && list1.get(i - 2).equals(list2.get(i - 1)));
                        }
                    } else {
                        return false;
                    }
                }
            }

        }
        return false;
    }


}
