package com.pxk;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author pxk
 * @date 2021年10月23日 17:00
 * <p>
 * 计算中缀表达式
 */
public class Calculation {
    /**
     * 对本类里的方法进行测试
     *
     * @param args
     */
    public static void main(String[] args) {
        String e = "2 × ( 1 ÷ ( 4'1/8 ÷ 2'2/3 ) ) ";
        System.out.println(toInfixExpressionList(e));
        System.out.println(toSuffixExpressionList(toInfixExpressionList(e)));
        System.out.println(calculate(toSuffixExpressionList(toInfixExpressionList(e))));
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
        String[] temp = infixExpression.split(" ");
        for (String ele : temp) {
            list.add(ele);
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
     * 传入一个后缀表达式对应的List，计算结果
     *
     * @param suffixExpressionList
     * @return
     */
    private static String calculate(List<String> suffixExpressionList) {
        // 创建栈，只需要一个即可
        Stack<String> stack = new Stack<>();
        // 遍历list
        for (String item : suffixExpressionList) {
            if (item.equals("×")
                    || item.equals("÷")
                    || item.equals("+")
                    || item.equals("-")) {  // 匹配的是运算符
                // pop出两个数，并运算，在入栈
                String num2 = stack.pop();
                String num1 = stack.pop();
                String res = actualCalculate(num1, num2, item);
                // 将结果入栈
                stack.push(res);
            } else {
                // 匹配的是数字，直接入栈
                stack.push(item);
            }
        }

        // 最后留在stack中的就是运算结果
        return stack.pop();
    }

    /**
     * 实际计算结果的方法
     *
     * @param num2
     * @param num1
     * @param operator
     * @return
     */
    private static String actualCalculate(String num1, String num2, String operator) {
        Fraction res = null;
        Fraction f1 = null;
        Fraction f2 = null;

        if (num1.contains("/")
                && num1.contains("'")) {
            String[] str = num1.split("'");
            String[] str1 = str[1].split("/");
            f1 = new Fraction(Integer.parseInt(str[0]) * Integer.parseInt(str1[1]) + Integer.parseInt(str1[0]), Integer.parseInt(str1[1]));

        } else if (num1.contains("/")
                && !num1.contains("'")) {
            String[] str = num1.split("/");
            f1 = new Fraction(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
        } else {
            f1 = new Fraction(Integer.parseInt(num1), 1);
        }

        if (num2.contains("/")
                && num2.contains("'")) {
            String[] str = num2.split("'");
            String[] str1 = str[1].split("/");
            f2 = new Fraction(Integer.parseInt(str[0]) * Integer.parseInt(str1[1]) + Integer.parseInt(str1[0]), Integer.parseInt(str1[1]));

        } else if (num2.contains("/")
                && !num2.contains("'")) {
            String[] str = num2.split("/");
            f2 = new Fraction(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
        } else {
            f2 = new Fraction(Integer.parseInt(num2), 1);
        }

        if (operator.equals("×")) {
            res = f1.mul(f1, f2);
        }
        if (operator.equals("÷")) {
            res = f1.div(f1, f2);
        }
        if (operator.equals("+")) {
            res = f1.add(f1, f2);
        }
        if (operator.equals("-")) {
            res = f1.sub(f1, f2);
        }
        return "" + res;

    }

    /**
     * 对以上所有方法进行封装
     *
     * @param expression 计算式
     */
    public static String getResult(String expression) {
        List<String> infixExpressionList = toInfixExpressionList(expression);

        List<String> suffixExpressionList = toSuffixExpressionList(infixExpressionList);

        return calculate(suffixExpressionList);
    }
}
