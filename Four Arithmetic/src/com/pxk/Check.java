package com.pxk;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pxk
 * @date 2021年10月23日 21:01
 * 检查是否生成重复的计算式
 */
public class Check {

    /**
     * 将逆波兰式的字符串转换为集合
     *
     * @param str 字符串
     * @return
     */

    public static List<String> stringToList(String str) {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        while (sb.length() > 0) {
            if (sb.charAt(0) == ' ') {
                sb.delete(0, 1);
                continue;
            }
            if (isOperator(String.valueOf(sb.charAt(0)))) {
                list.add(String.valueOf(sb.charAt(0)));
            } else {
                list.add(sb.substring(0, sb.indexOf(" ")));
            }
            sb.delete(0, sb.indexOf(" "));
        }
        return list;
    }


    /**
     * 判断一个字符串是否为运算符号
     *
     * @param str 运算符号
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
     * @param arrayList_1 逆波兰式集合_1
     * @param operator_1  逆波兰式集合_1中参与对比的运算符的下标
     * @param arrayList_2 逆波兰式集合_2
     * @param operator_2  逆波兰式集合_2中参与对比的运算符的下标
     * @return 相同返回true，不相同返回false
     */
    public static boolean isExchange(ArrayList<String> arrayList_1, int operator_1, ArrayList<String> arrayList_2, int operator_2) {
        // 该函数主要通过判断 运算符的类型， 以及 两个参与运算数顺序是否相同或可进行互换后相同，来判断两个式子是否相同

        // 运算符是否相等
        if (!arrayList_1.get(operator_1).equals(arrayList_2.get(operator_2))) {
            return false;
        }
        // 运算符是否为除号或减号，要求参与运算的数的顺序必须相同，不能交换
        if (arrayList_1.get(operator_1).equals("÷") || arrayList_1.get(operator_1).equals("-")) {
            // 例：2 1 - 和 2 1 - => true
            //    2 1 - 和 1 2 - => false
            return arrayList_1.get(operator_1 - 2).equals(arrayList_2.get(operator_2 - 2)) && arrayList_1.get(operator_1 - 1).equals(arrayList_2.get(operator_2 - 1));
        }
        // 运算符为加号或乘号，参与运算的数允许交换
        else {
            // 例：（ 1 2 + 和 1 2 + ）或 （ 1 2 + 和 2 1 + ) => true
            //     1 2 + 和 2 3 + => false
            return arrayList_1.get(operator_1 - 2).equals(arrayList_2.get(operator_2 - 2)) && arrayList_1.get(operator_1 - 1).equals(arrayList_2.get(operator_2 - 1))
                    || arrayList_1.get(operator_1 - 1).equals(arrayList_2.get(operator_2 - 2)) && arrayList_1.get(operator_1 - 2).equals(arrayList_2.get(operator_2 - 1));
        }
    }


    /**
     * 判断两个式子是否能通过有限次交互+×的到相同的式子
     *
     * @param arrayList_1 逆波兰式集合_1
     * @param arrayList_2 逆波兰式集合_2
     * @return 相同返回true，不相同返回false
     */
    public static boolean examine(ArrayList<String> arrayList_1, ArrayList<String> arrayList_2) {
        // 判断两个字符串长度是否相同
        if (arrayList_1.size() != arrayList_2.size()) {
            return false;
        }

        // 逆波兰式中，第一次出现运算符的下标要么为 2，要么为 3
        // 例：1 2 3 + + => operatorIndex=3 或 1 2 + 3 + => operatorIndex=2
        int operatorIndex_1 = isOperator(arrayList_1.get(2)) ? 2 : 3; // 逆波兰式集合_1第一次出现运算符的下标
        int operatorIndex_2 = isOperator(arrayList_2.get(2)) ? 2 : 3; // 逆波兰式集合_2第一次出现运算符的下标
        // 第一次对比后，若相同，用一个字符串替代运算结果加入集合的下标位置
        // 例：3 1 2 + + addIndex=1 => 3 A +
        //    1 2 + 3 + addIndex=0 => A 3 +
        int addIndex_1 = operatorIndex_1 - 2;
        int addIndex_2 = operatorIndex_2 - 2;

        // 两个式子皆为三个运算符+两个括号的情况
        // 原式：(1+2)+(3+4)=  => 逆波兰式：1 2 + 3 4 + +
        if (arrayList_1.size() == 7 && isOperator(arrayList_1.get(2)) && isOperator(arrayList_1.get(5)) && isOperator(arrayList_2.get(2)) && isOperator(arrayList_2.get(5))) {
            // 判断最后一个运算符是否相同
            if (arrayList_1.get(6).equals(arrayList_2.get(6))) {
                // 两个括号整体相同
                // 例：1 2 + 3 4 + + 和 2 1 + 4 3 + +
                if (isExchange(arrayList_1, 2, arrayList_2, 2)) {
                    return isExchange(arrayList_1, 5, arrayList_2, 5);
                }
                // 两个括号整体交换
                // 例：1 2 + 3 4 + + 和 4 3 + 2 1 + +
                // 当原式中间运算符为 - 或 ÷，两个括号整体不允许交换
                else if (isExchange(arrayList_1, 2, arrayList_2, 5) && arrayList_1.get(6).equals("-") && arrayList_1.get(6).equals("÷")) {
                    return isExchange(arrayList_1, 5, arrayList_2, 2);
                } else return false;
            } else return false;
        } else {
            while (arrayList_1.size() > 1) {
                // 按运算顺序，判断最优先运算的运算符与其两个运算式子是否相同
                // 例：1 2 3 + + 先取出 2 3 + 判断
                if (!isExchange(arrayList_1, operatorIndex_1, arrayList_2, operatorIndex_2)) {
                    return false;
                } else {
                    // 若相同的情况，将刚才参与运算符与运算数用一个字符串替代
                    // 3 1 2 + + => 3 A + 和 1 2 + 3 + => A 3 +
                    for (int i = 0; i < 3; i++) {
                        arrayList_1.remove(operatorIndex_1 - 2);
                        arrayList_2.remove(operatorIndex_2 - 2);
                    }
                    arrayList_1.add(addIndex_1, "A");
                    arrayList_2.add(addIndex_2, "A");
                    operatorIndex_1 = 2;
                    operatorIndex_2 = 2;
                    addIndex_1 = addIndex_2 = 0;
                }
            }
        }
        return true;
    }
}
