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
     * 判断两个只有一位运算符的式子是否能通过有限次交互+×的到相同的式子
     *
     * @param list1      逆波兰式集合_1
     * @param operator_1 逆波兰式集合_1中参与对比的运算符的下标
     * @param list2      逆波兰式集合_2
     * @param operator_2 逆波兰式集合_2中参与对比的运算符的下标
     * @return 相同返回true，不相同返回false
     */
    public static boolean isExchange(List<String> list1, int operator_1, List<String> list2, int operator_2) {
        // 该函数主要通过判断 运算符的类型， 以及 两个参与运算数顺序是否相同或可进行互换后相同，来判断两个式子是否相同

        // 运算符是否相等
        if (!list1.get(operator_1).equals(list2.get(operator_2))) {
            return false;
        }
        // 运算符是否为除号或减号，要求参与运算的数的顺序必须相同，不能交换
        if (list1.get(operator_1).equals("÷") || list1.get(operator_1).equals("-")) {
            // 例：2 1 - 和 2 1 - => true
            //    2 1 - 和 1 2 - => false
            return list1.get(operator_1 - 2).equals(list2.get(operator_2 - 2)) && list1.get(operator_1 - 1).equals(list1.get(operator_2 - 1));
        }
        // 运算符为加号或乘号，参与运算的数允许交换
        else {
            // 例：（ 1 2 + 和 1 2 + ）或 （ 1 2 + 和 2 1 + ) => true
            //     1 2 + 和 2 3 + => false
            return list1.get(operator_1 - 2).equals(list2.get(operator_2 - 2)) && list1.get(operator_1 - 1).equals(list2.get(operator_2 - 1))
                    || list1.get(operator_1 - 1).equals(list2.get(operator_2 - 2)) && list1.get(operator_1 - 2).equals(list2.get(operator_2 - 1));
        }
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
        } else if (Calculation.getResult(expression_1).equals(Calculation.getResult(expression_2))) {   // 结果不同
            return false;
        }

        // 逆波兰式中，第一次出现运算符的下标要么为 2，要么为 3
        // 例：1 2 3 + + => operatorIndex=3 或 1 2 + 3 + => operatorIndex=2
        int operatorIndex_1 = isOperator(list1.get(2)) ? 2 : 3; // 逆波兰式集合_1第一次出现运算符的下标
        int operatorIndex_2 = isOperator(list1.get(2)) ? 2 : 3; // 逆波兰式集合_2第一次出现运算符的下标
        // 第一次对比后，若相同，用一个字符串替代运算结果加入集合的下标位置
        // 例：3 1 2 + + addIndex=1 => 3 A +
        //    1 2 + 3 + addIndex=0 => A 3 +
        int addIndex_1 = operatorIndex_1 - 2;
        int addIndex_2 = operatorIndex_2 - 2;

        // 两个式子皆为三个运算符+两个括号的情况
        // 原式：(1+2)+(3+4)=  => 逆波兰式：1 2 + 3 4 + +
        if (list1.size() == 7 && isOperator(list1.get(2)) && isOperator(list1.get(5)) && isOperator(list2.get(2)) && isOperator(list2.get(5))) {
            // 判断最后一个运算符是否相同
            if (list1.get(6).equals(list2.get(6))) {
                // 两个括号整体相同
                // 例：1 2 + 3 4 + + 和 2 1 + 4 3 + +
                if (isExchange(list1, 2, list2, 2)) {
                    return isExchange(list1, 5, list2, 5);
                }
                // 两个括号整体交换
                // 例：1 2 + 3 4 + + 和 4 3 + 2 1 + +
                // 当原式中间运算符为 - 或 ÷，两个括号整体不允许交换
                else if (isExchange(list1, 2, list2, 5) && list1.get(6).equals("-") && list1.get(6).equals("÷")) {
                    return isExchange(list1, 5, list2, 2);
                } else return false;
            } else return false;
        } else {
            while (list1.size() > 1) {
                // 按运算顺序，判断最优先运算的运算符与其两个运算式子是否相同
                // 例：1 2 3 + + 先取出 2 3 + 判断
                if (!isExchange(list1, operatorIndex_1, list2, operatorIndex_2)) {
                    return false;
                } else {
                    // 若相同的情况，将刚才参与运算符与运算数用一个字符串替代
                    // 3 1 2 + + => 3 A + 和 1 2 + 3 + => A 3 +
                    for (int i = 0; i < 3; i++) {
                        list1.remove(operatorIndex_1 - 2);
                        list2.remove(operatorIndex_2 - 2);
                    }
                    list1.add(addIndex_1, "A");
                    list2.add(addIndex_2, "A");
                    operatorIndex_1 = 2;
                    operatorIndex_2 = 2;
                    addIndex_1 = addIndex_2 = 0;
                }
            }
        }
        return true;
    }

}
