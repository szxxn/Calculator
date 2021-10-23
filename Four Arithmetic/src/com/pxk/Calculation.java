package com.pxk;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author pxk
 * @date 2021年10月23日 17:00
 */
public class Calculation {

    /**
     * 判断是不是非数字（包括'）
     *
     * @param c
     * @return
     */
    private static boolean isSymbol(char c) {
        return (c == '('
                || c == ')'
                || c == '+'
                || c == '-'
                || c == '÷'
                || c == '×');
    }

    /**
     * 将中缀表达式转成对应的List
     *
     * @param infixExpression 中缀表达式（每个字符之间没有使用空格隔开）
     * @return
     */
    private static List<String> toInfixExpressionList(String infixExpression) {
        // 定义一个List，存放中缀表达式对应的内容
        List<String> list = new ArrayList<>();
        // 相当于一个指针，用于遍历中缀表达式字符串
        int index = 0;
        // 用于多位数的拼接
        String str;

        while (index < infixExpression.length()) {
            // 如果c是一个非数字，直接加入到List
            if (isSymbol(infixExpression.charAt(index))) {
                list.add("" + infixExpression.charAt(index));
                index++;    // 指针后移
            } else {  // 如果c是数字,包括'
                // 先将str置空
                str = "";
                while (index < infixExpression.length()
                        && !isSymbol(infixExpression.charAt(index))) {
                    str += infixExpression.charAt(index);
                    index++;    // 指针后移
                }
                list.add(str);
            }

        }

        return list;
    }

    /**
     * 将中缀表达式对应的List，转换成，后缀表达式对应的List
     *
     * @param infixExpressionList
     * @return
     */
    private static List<String> toSuffixExpressionList(List<String> infixExpressionList) {
        Stack<String> s1 = new Stack<>();   // 符号栈
        List<String> s2 = new ArrayList<>();   // 存储中间结果的集合

        // 遍历中缀表达式的存储集合
        for (String item : infixExpressionList) {
            // 如果是运算符
            if (item.equals("×")
                    || item.equals("÷")
                    || item.equals("+")
                    || item.equals("-")) {
                // 当s1栈顶的运算符的优先级 >= item运算符的优先级
                // 缺少比较运算符优先级高低的方法
                while (s1.size() != 0
                        && getPriority(s1.peek()) >= getPriority(item)) {
                    s2.add(s1.pop());
                }
                s1.push(item);  // item入栈
            } else if (item.equals("(")) {   // 左括号
                s1.push(item);
            } else if (item.equals(")")) { // 右括号
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();   // 将 ( 小括号弹出栈 ==》 消除小括号
            } else {    // 数字
                s2.add(item);
            }
        }

        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;
    }

    /**
     * 传入一个后缀表达式对应的List，计算结果
     *
     * @param suffixExpressionList
     * @return
     */
    private static double calculate(List<String> suffixExpressionList) {
        // 创建栈，只需要一个即可
        Stack<String> stack = new Stack<>();
        // 遍历list
        for (String item : suffixExpressionList) {
            if (item.equals("×")
                    || item.equals("÷")
                    || item.equals("+")
                    || item.equals("-")) {  // 匹配的是运算符
                // pop出两个数，并运算，在入栈
                double num2 = toDouble(stack.pop());
                double num1 = toDouble(stack.pop());
                double res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("×")) {
                    res = num1 * num2;
                } else if (item.equals("÷")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误！");
                }
                // 将结果入栈
                stack.push("" + res);
            } else {
                // 匹配的是数字，直接入栈
                stack.push(item);
            }
        }

        // 最后留在stack中的就是运算结果
        return Double.parseDouble(stack.pop());
    }

    /**
     * 将分数转换成double类型的数字
     * 共有3中形式：整数、不带'的分数，带'的分数
     *
     * @param num
     * @return
     */
    private static double toDouble(String num) {
        if (num.contains("/")
                && num.contains("'")) {
            String[] str = num.split("'");
            String[] str1 = str[1].split("/");
            return Double.parseDouble(str[0])
                    + Double.parseDouble(str1[0]) / Double.parseDouble(str1[1]);
        } else if (num.contains("/")
                && !num.contains("'")) {
            String[] str = num.split("/");
            return Double.parseDouble(str[0]) / Double.parseDouble(str[1]);
        } else {
            return Double.parseDouble(num);
        }
    }

    /**
     * 获取运算符的优先级
     *
     * @param operation
     * @return
     */
    private static int getPriority(String operation) {
        if (operation.equals("×") || operation.equals("÷")) {
            return 1;
        } else if (operation.equals("+") || operation.equals("-")) {
            return 0;
        } else {
            return -1;  // 假定目前的表达式只有"+", "-", "×", "÷"/
        }
    }

    /**
     * 对以上所有方法进行封装
     *
     * @param expression 计算式
     */
    public static double getResult(String expression) {
        List<String> infixExpressionList = toInfixExpressionList(expression);

        List<String> suffixExpressionList = toSuffixExpressionList(infixExpressionList);

        return calculate(suffixExpressionList);
    }
}
